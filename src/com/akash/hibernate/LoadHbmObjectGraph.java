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
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.hcentive.eligibility.ssap.domain.SsapEligibility;

public class LoadHbmObjectGraph {

	public static void loadObjGraph(EntityManager entityManager) {

		System.out.println("<---------reading object graph---------->");
		String objectPath = "eligibility";
		Set<EntityIdCache> entityIdCache = new HashSet<EntityIdCache>();
		try {
			Object ob = entityManager.find(SsapEligibility.class, 427290l);
			loadElg(entityManager, entityIdCache, ob, 427290l, objectPath);
		} catch (Exception e) {
			System.out.println(e);
			for (StackTraceElement stackTrace : e.getStackTrace()) {
				System.out.println(stackTrace);
			}
		}
		System.out.println("<----------Object graph ends----------->");

	}

	private static void loadElg(EntityManager entityManager, Set<EntityIdCache> entityIdCache, Object ob, Object id,
			String objectPath) {

		// TODO : code for Map entries in entity
		// TODO : handling for embeddable
		// TODO : check entity/embeddable annotation at class level
		EntityIdCache cacheObj = new EntityIdCache(ob.getClass().getName(), id);
		if (entityIdCache.contains(cacheObj)) {
			return;
		}
		entityIdCache.add(cacheObj);
		Class<?> clazz = ob.getClass();

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
						|| (!field.getType().getName().startsWith("com.hcentive") && !Collection.class
								.isAssignableFrom(field.getType()))) {
					try {
						System.out.println(objectPath + "." + field.getName() + "\t\t\t:\t" + field.get(ob));
					} catch (Exception e) {
						System.out.println(e);
						for (StackTraceElement stackTrace : e.getStackTrace()) {
							System.out.println(stackTrace);
						}
					}
				} else if (Collection.class.isAssignableFrom(field.getType())) {
					try {

						Collection<?> collectionObj = (Collection<?>) new PropertyDescriptor(field.getName(), clazz,
								"get" + capitalize(field.getName()), "set" + capitalize(field.getName()))
								.getReadMethod().invoke(ob);
						int counter = 0;
						if (collectionObj != null) {
							for (Object nestedObj : collectionObj) {
								nestedObj = initialize(nestedObj);
								Object primaryKey = getPrimaryKey(nestedObj);
								if (primaryKey != null) {
									Object nestedObjFromDb = entityManager.find(nestedObj.getClass(), primaryKey);
									loadElg(entityManager, entityIdCache, nestedObjFromDb, primaryKey, objectPath + "."
											+ field.getName() + "[" + counter + "]");
									counter++;
								}
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						for (StackTraceElement stackTrace : e.getStackTrace()) {
							System.out.println(stackTrace);
						}
					}
				} else if (!field.isEnumConstant() && field.getType().getName().startsWith("com.hcentive")) {
					try {
						Object nestedObj = new PropertyDescriptor(field.getName(), clazz, "get"
								+ capitalize(field.getName()), "set" + capitalize(field.getName())).getReadMethod()
								.invoke(ob);
						if (nestedObj != null) {
							nestedObj = initialize(nestedObj);
							Object primaryKey = getPrimaryKey(nestedObj);
							if (primaryKey != null) {
								Object nestedObjFromDb = entityManager.find(nestedObj.getClass(), primaryKey);
								loadElg(entityManager, entityIdCache, nestedObjFromDb, primaryKey, objectPath + "."
										+ field.getName());
							}
						}
					} catch (Exception e) {
						System.out.println(e);
						for (StackTraceElement stackTrace : e.getStackTrace()) {
							System.out.println(stackTrace);
						}
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
			if (annotation.annotationType().getName().equals("javax.persistence.Transient")) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasColumnAnnotation(Field field) {
		Annotation[] annotations = field.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().equals("javax.persistence.Column")) {
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
				if (annotation.annotationType().getName().equals("javax.persistence.Id")) {
					field.setAccessible(true);
					try {
						return field.get(obj);
					} catch (Exception e) {
						System.out.println(e);
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