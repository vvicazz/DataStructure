package com.akash.hibernate;

import static java.util.Locale.ENGLISH;

/*import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LoadHbmObjectGraph {

	private static final Logger logger = LoggerFactory.getLogger(LoadHbmObjectGraph.class);

	private static final String PACKAGE_TO_SCAN = "com.hcentive";
	private static final String JPA_TRANSIENT_ANNOTATION = "javax.persistence.Transient";
	private static final String JPA_COLUMN_ANNOTATION = "javax.persistence.Column";
	private static final String JPA_ID_ANNOTATION = "javax.persistence.Id";
	private static final String GET_METHOD_PREFIX = "get";
	private static final String SET_METHOD_PREFIX = "set";
	private static final String DOT = ".";

	static String loadObjGraph(EntityManager entityManager, Class<?> classToLoad, Object primaryKey) {

		StringBuilder objectGraphContent = new StringBuilder();
		String objectPath = null;
		if (classToLoad.getName().contains(DOT)) {
			objectPath = classToLoad.getName().substring(classToLoad.getName().lastIndexOf(DOT) + 1,
					classToLoad.getName().length());
		} else {
			objectPath = classToLoad.getName();
		}
		Set<EntityIdCache> entityIdCache = new HashSet<EntityIdCache>();
		try {
			Object ob = entityManager.find(classToLoad, primaryKey);
			loadElg(entityManager, entityIdCache, ob, primaryKey, objectPath, objectGraphContent);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return objectGraphContent.toString();
	}

	private static void loadElg(EntityManager entityManager, Set<EntityIdCache> entityIdCache, Object objectToLoad,
			Object primaryKey, String objectPath, StringBuilder objectGraphContent) {
		// TODO : code for Map entries in entity
		// TODO : handling for embeddable
		// TODO : check entity/embeddable annotation at class level
		EntityIdCache cacheObj = new EntityIdCache(objectToLoad.getClass().getName(), primaryKey);
		if (entityIdCache.contains(cacheObj)) {
			return;
		}
		entityIdCache.add(cacheObj);
		Class<?> clazz = objectToLoad.getClass();
		List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
		Class<?> current = clazz;
		while (true) {
			current = current.getSuperclass();
			if (current.getSuperclass() == null) {
				break;
			}
			fields.addAll(new ArrayList<>(Arrays.asList(current.getDeclaredFields())));
		}
		for (Field field : fields) {
			field.setAccessible(true);
			if (!isTransient(field) && field.getAnnotations().length > 0 && field.getModifiers() != Modifier.STATIC) {
				if (hasColumnAnnotation(field)
						&& field.isEnumConstant()
						|| (!field.getType().getName().startsWith(PACKAGE_TO_SCAN) && !Collection.class
								.isAssignableFrom(field.getType()))) {
					try {
						objectGraphContent.append(objectPath + DOT + field.getName() + "\t\t:" + field.get(objectToLoad) + "\r\n");
					} catch (Exception e) {
						logger.error(e.toString(), e);
					}
				} else if (Collection.class.isAssignableFrom(field.getType())) {
					try {
						Collection<?> collectionObj = (Collection<?>) new PropertyDescriptor(field.getName(), clazz,
								GET_METHOD_PREFIX + capitalize(field.getName()), SET_METHOD_PREFIX
										+ capitalize(field.getName())).getReadMethod().invoke(objectToLoad);
						collectionObj = initialize(collectionObj);
						int counter = 0;
						if (collectionObj != null) {
							for (Object nestedObj : collectionObj) {
								Object nestedObjectPrimaryKey = getPrimaryKey(nestedObj);
								if (nestedObjectPrimaryKey != null) {
									Object nestedObjFromDb = entityManager.find(nestedObj.getClass(),
											nestedObjectPrimaryKey);
									loadElg(entityManager, entityIdCache, nestedObjFromDb, nestedObjectPrimaryKey,
											objectPath + DOT + field.getName() + "[" + counter + "]",
											objectGraphContent);
									counter++;
								}
							}
						}
					} catch (Exception e) {
						logger.error(e.toString(), e);
					}
				} else if (!field.isEnumConstant() && field.getType().getName().startsWith(PACKAGE_TO_SCAN)) {
					try {
						Object nestedObj = new PropertyDescriptor(field.getName(), clazz, GET_METHOD_PREFIX
								+ capitalize(field.getName()), SET_METHOD_PREFIX + capitalize(field.getName()))
								.getReadMethod().invoke(objectToLoad);
						if (nestedObj != null) {
							nestedObj = initialize(nestedObj);
							Object nestedObjectPrimaryKey = getPrimaryKey(nestedObj);
							if (nestedObjectPrimaryKey != null) {
								Object nestedObjFromDb = entityManager.find(nestedObj.getClass(),
										nestedObjectPrimaryKey);
								loadElg(entityManager, entityIdCache, nestedObjFromDb, nestedObjectPrimaryKey,
										objectPath + DOT + field.getName(), objectGraphContent);
							}
						}
					} catch (Exception e) {
						logger.error(e.toString(), e);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T initialize(T entity) {
		if (entity instanceof HibernateProxy) {
			Hibernate.initialize(entity);
			entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
		}
		return entity;
	}

	private static String capitalize(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
	}

	private static boolean isTransient(Field field) {
		Annotation[] annotations = field.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().equals(JPA_TRANSIENT_ANNOTATION)) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasColumnAnnotation(Field field) {
		Annotation[] annotations = field.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().equals(JPA_COLUMN_ANNOTATION)) {
				return true;
			}
		}
		return false;
	}

	private static Object getPrimaryKey(Object obj) {
		if (obj == null) {
			return null;
		}
		List<Field> fields = new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredFields()));
		Class<?> current = obj.getClass();
		while (true) {
			current = current.getSuperclass();
			if (current.getSuperclass() == null) {
				break;
			}
			fields.addAll(new ArrayList<>(Arrays.asList(current.getDeclaredFields())));
		}
		for (Field field : fields) {
			field.setAccessible(true);
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getName().equals(JPA_ID_ANNOTATION)) {
					field.setAccessible(true);
					try {
						return field.get(obj);
					} catch (Exception e) {
						logger.error(e.toString(), e);
					}
				}
			}
		}
		return null;
	}

	private static class EntityIdCache {
		private final String className;
		private final Object id;

		public EntityIdCache(String className, Object id) {
			this.className = className;
			this.id = id;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((className == null) ? 0 : className.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EntityIdCache other = (EntityIdCache) obj;
			if (className == null) {
				if (other.className != null)
					return false;
			} else if (!className.equals(other.className))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
	}
}*/