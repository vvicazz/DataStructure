package com.akash.hibernate;

/*
import static java.util.Locale.ENGLISH;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LoadHbmObjectGraph {

	private static final Logger logger = LoggerFactory.getLogger(LoadHbmObjectGraph.class);

	private static final String PACKAGE_TO_SCAN = "com.base.package";
	private static final String JPA_TRANSIENT_ANNOTATION = "javax.persistence.Transient";
	private static final String JPA_ID_ANNOTATION = "javax.persistence.Id";
	private static final String JPA_ENTITY_ANNOTATION = "javax.persistence.Entity";
	private static final String JPA_EMBEDDED_ANNOTATION = "javax.persistence.Embedded";
	private static final String JPA_EMBEDDABLE_ANNOTATION = "javax.persistence.Embeddable";
	private static final String GET_METHOD_PREFIX = "get";
	private static final String SET_METHOD_PREFIX = "set";
	private static final String EMBEDDED_OBJECT_KEY = "EMBEDDED_OBJECT_KEY";
	private static final String DOT = ".";

	static String loadFullObjectGraph(EntityManager entityManager, Class<?> classToLoad, Object primaryKey) {
		if (!hasAnnotation(classToLoad, JPA_ENTITY_ANNOTATION)) {
			return "Not a valid JPA Entity : " + classToLoad;
		}
		StringBuilder objectGraphContent = new StringBuilder();
		String objectPath = getObjectPath(classToLoad);
		Set<EntityIdInfo> entityIdCache = new HashSet<EntityIdInfo>();
		try {
			Object ob = entityManager.find(classToLoad, primaryKey);
			loadObject(entityManager, entityIdCache, ob, primaryKey, objectPath, objectGraphContent);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return objectGraphContent.toString();
	}

	private static void loadObject(EntityManager entityManager, Set<EntityIdInfo> loadedEntityCache,
			Object objectToLoad, Object primaryKey, String objectPath, StringBuilder objectGraphContent) {
		if (!EMBEDDED_OBJECT_KEY.equals(primaryKey)) {
			EntityIdInfo entityIdInfo = new EntityIdInfo(objectToLoad.getClass().getName(), primaryKey);
			if (loadedEntityCache.contains(entityIdInfo)) {
				return;
			}
			loadedEntityCache.add(entityIdInfo);
		}
		Class<?> objectToLoadClass = objectToLoad.getClass();
		List<Field> fieldsToScan = getAllFields(objectToLoadClass);
		for (Field fieldToRead : fieldsToScan) {
			fieldToRead.setAccessible(true);
			if (!hasAnnotation(fieldToRead, JPA_TRANSIENT_ANNOTATION) && fieldToRead.getModifiers() != Modifier.STATIC) {
				if (fieldToRead.isEnumConstant()
						|| (!fieldToRead.getType().getName().startsWith(PACKAGE_TO_SCAN)
								&& !Collection.class.isAssignableFrom(fieldToRead.getType()) && !Map.class
									.isAssignableFrom(fieldToRead.getType()))) {
					try {
						objectGraphContent.append(objectPath).append(DOT).append(fieldToRead.getName()).append("\t\t:")
								.append(fieldToRead.get(objectToLoad)).append("\r\n");
					} catch (Exception e) {
						logger.error(e.getMessage() + " in class " + objectToLoad.getClass() + " while reading :"
								+ fieldToRead, e);
					}
				} else if (Collection.class.isAssignableFrom(fieldToRead.getType())) {
					try {
						Collection<?> collectionObj = (Collection<?>) new PropertyDescriptor(fieldToRead.getName(),
								objectToLoadClass, GET_METHOD_PREFIX + capitalize(fieldToRead.getName()),
								SET_METHOD_PREFIX + capitalize(fieldToRead.getName())).getReadMethod().invoke(
								objectToLoad);
						if (collectionObj != null) {
							collectionObj = initialize(collectionObj);
							int counter = 0;
							for (Object nestedObj : collectionObj) {
								if (hasAnnotation(fieldToRead, JPA_EMBEDDED_ANNOTATION)
										&& hasAnnotation(nestedObj.getClass(), JPA_EMBEDDABLE_ANNOTATION)) {
									loadObject(entityManager, loadedEntityCache, nestedObj, EMBEDDED_OBJECT_KEY,
											objectPath + DOT + fieldToRead.getName() + "[" + counter + "]",
											objectGraphContent);
								} else {
									if (!hasAnnotation(nestedObj.getClass(), JPA_ENTITY_ANNOTATION)) {
										break;
									}
									Object nestedObjectPrimaryKey = getPrimaryKey(nestedObj);
									if (nestedObjectPrimaryKey != null) {
										Object nestedObjFromDb = entityManager.find(nestedObj.getClass(),
												nestedObjectPrimaryKey);
										loadObject(entityManager, loadedEntityCache, nestedObjFromDb,
												nestedObjectPrimaryKey, objectPath + DOT + fieldToRead.getName() + "["
														+ counter + "]", objectGraphContent);
									}
								}
								counter++;
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage() + " while reading :" + fieldToRead, e);
					}
				} else if (!fieldToRead.isEnumConstant() && fieldToRead.getType().getName().startsWith(PACKAGE_TO_SCAN)) {
					try {
						Object nestedObj = new PropertyDescriptor(fieldToRead.getName(), objectToLoadClass,
								GET_METHOD_PREFIX + capitalize(fieldToRead.getName()), SET_METHOD_PREFIX
										+ capitalize(fieldToRead.getName())).getReadMethod().invoke(objectToLoad);
						if (nestedObj != null) {
							nestedObj = initialize(nestedObj);
							if (hasAnnotation(fieldToRead, JPA_EMBEDDED_ANNOTATION)
									&& hasAnnotation(nestedObj.getClass(), JPA_EMBEDDABLE_ANNOTATION)) {
								loadObject(entityManager, loadedEntityCache, nestedObj, EMBEDDED_OBJECT_KEY, objectPath
										+ DOT + fieldToRead.getName(), objectGraphContent);
							} else if (hasAnnotation(fieldToRead.getType(), JPA_ENTITY_ANNOTATION)) {
								Object nestedObjectPrimaryKey = getPrimaryKey(nestedObj);
								if (nestedObjectPrimaryKey != null) {
									Object nestedObjFromDb = entityManager.find(nestedObj.getClass(),
											nestedObjectPrimaryKey);
									loadObject(entityManager, loadedEntityCache, nestedObjFromDb,
											nestedObjectPrimaryKey, objectPath + DOT + fieldToRead.getName(),
											objectGraphContent);
								}
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage() + " while reading:" + fieldToRead, e);
					}
				} else if (Map.class.isAssignableFrom(fieldToRead.getType())) {
					// TODO
				}
			}
		}
	}

	private static String getObjectPath(Class<?> classToLoad) {
		String objectPath = "";
		if (classToLoad.getName().contains(DOT)) {
			objectPath = classToLoad.getName().substring(classToLoad.getName().lastIndexOf(DOT) + 1,
					classToLoad.getName().length());
		} else {
			objectPath = classToLoad.getName();
		}
		return objectPath;
	}

	private static List<Field> getAllFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
		Class<?> currentClass = clazz;
		while (true) {
			currentClass = currentClass.getSuperclass();
			if (currentClass.getSuperclass() == null) {
				break;
			}
			fields.addAll(new ArrayList<>(Arrays.asList(currentClass.getDeclaredFields())));
		}
		return fields;
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

	private static Object getPrimaryKey(Object obj) {
		if (obj == null) {
			return null;
		}
		List<Field> fields = getAllFields(obj.getClass());
		for (Field field : fields) {
			field.setAccessible(true);
			for (Annotation annotation : field.getAnnotations()) {
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

	static private boolean hasAnnotation(Field field, String annotationName) {
		for (Annotation annotation : field.getAnnotations()) {
			if (annotation.annotationType().getName().equals(annotationName)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	static private boolean hasAnnotation(Class<?> clazz, String annotationName) {
		for (Annotation annotation : clazz.getAnnotations()) {
			if (annotation.annotationType().getName().equals(annotationName)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	private static class EntityIdInfo {
		private final String className;
		private final Object id;

		public EntityIdInfo(String className, Object id) {
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
			EntityIdInfo other = (EntityIdInfo) obj;
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