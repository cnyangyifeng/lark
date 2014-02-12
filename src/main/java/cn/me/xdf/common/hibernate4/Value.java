package cn.me.xdf.common.hibernate4;

import java.util.Collection;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.NaturalIdentifier;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class Value {

	Value() {
		// cannot be instantiated
	}

	public static Criterion[] asValues(Criterion... criterions) {
		if (criterions != null) {
			Criterion[] values = new Criterion[criterions.length];
			for (int i = 0; i < criterions.length; i++) {
				values[i] = criterions[i];
			}
			return values;
		} else {
			return null;
		}
	}

	/**
	 * Apply an "equal" constraint to the identifier property
	 * 
	 * @param value
	 * @return Criterion
	 */
	public static Criterion idEq(Object value) {
		return Restrictions.idEq(value);
	}

	/**
	 * Apply an "equal" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression eq(String propertyName, Object value) {
		return Restrictions.eq(propertyName, value);
	}

	/**
	 * Apply a "not equal" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression ne(String propertyName, Object value) {
		return Restrictions.ne(propertyName, value);
	}

	/**
	 * Apply a "like" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression like(String propertyName, Object value) {
		return Restrictions.like(propertyName, value);
	}

	/**
	 * Apply a "like" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression like(String propertyName, String value,
			MatchMode matchMode) {
		return Restrictions.like(propertyName, value, matchMode);
	}

	/**
	 * A case-insensitive "like", similar to Postgres <tt>ilike</tt> operator
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static Criterion ilike(String propertyName, String value,
			MatchMode matchMode) {
		return Restrictions.ilike(propertyName, value, matchMode);
	}

	/**
	 * A case-insensitive "like", similar to Postgres <tt>ilike</tt> operator
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static Criterion ilike(String propertyName, Object value) {
		return Restrictions.ilike(propertyName, value);
	}

	/**
	 * Apply a "greater than" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression gt(String propertyName, Object value) {
		return Restrictions.ge(propertyName, value);
	}

	/**
	 * Apply a "less than" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression lt(String propertyName, Object value) {
		return Restrictions.lt(propertyName, value);
	}

	/**
	 * Apply a "less than or equal" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression le(String propertyName, Object value) {
		return Restrictions.le(propertyName, value);
	}

	/**
	 * Apply a "greater than or equal" constraint to the named property
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression ge(String propertyName, Object value) {
		return Restrictions.ge(propertyName, value);
	}

	/**
	 * Apply a "between" constraint to the named property
	 * 
	 * @param propertyName
	 * @param lo
	 *            value
	 * @param hi
	 *            value
	 * @return Criterion
	 */
	public static Criterion between(String propertyName, Object lo, Object hi) {
		return Restrictions.between(propertyName, lo, hi);
	}

	/**
	 * Apply an "in" constraint to the named property
	 * 
	 * @param propertyName
	 * @param values
	 * @return Criterion
	 */
	public static Criterion in(String propertyName, Object[] values) {
		return Restrictions.in(propertyName, values);
	}

	/**
	 * Apply an "in" constraint to the named property
	 * 
	 * @param propertyName
	 * @param values
	 * @return Criterion
	 */
	public static Criterion in(String propertyName, Collection<?> values) {
		return Restrictions.in(propertyName, values);
	}

	/**
	 * Apply an "is null" constraint to the named property
	 * 
	 * @return Criterion
	 */
	public static Criterion isNull(String propertyName) {
		return Restrictions.isNull(propertyName);
	}

	/**
	 * Apply an "is not null" constraint to the named property
	 * 
	 * @return Criterion
	 */
	public static Criterion isNotNull(String propertyName) {
		return Restrictions.isNotNull(propertyName);
	}

	/**
	 * Return the conjuction of two expressions
	 * 
	 * @param lhs
	 * @param rhs
	 * @return Criterion
	 */
	public static LogicalExpression and(Criterion lhs, Criterion rhs) {
		return Restrictions.and(lhs, rhs);
	}

	/**
	 * Return the conjuction of multiple expressions
	 * 
	 * @param predicates
	 *            The predicates making up the initial junction
	 * 
	 * @return The conjunction
	 */
	public static Conjunction and(Criterion... predicates) {
		return Restrictions.and(predicates);
	}

	/**
	 * Return the disjuction of two expressions
	 * 
	 * @param lhs
	 * @param rhs
	 * @return Criterion
	 */
	public static LogicalExpression or(Criterion lhs, Criterion rhs) {
		return Restrictions.or(lhs, rhs);
	}

	/**
	 * Return the disjuction of multiple expressions
	 * 
	 * @param predicates
	 *            The predicates making up the initial junction
	 * 
	 * @return The conjunction
	 */
	public static Disjunction or(Criterion... predicates) {
		return Restrictions.or(predicates);
	}

	/**
	 * Apply an "equals" constraint to each property in the key set of a
	 * <tt>Map</tt>
	 * 
	 * @param propertyNameValues
	 *            a map from property names to values
	 * @return Criterion
	 */
	public static Criterion allEq(Map<?, ?> propertyNameValues) {
		return Restrictions.allEq(propertyNameValues);
	}

	/**
	 * Constrain a collection valued property to be empty
	 */
	public static Criterion isEmpty(String propertyName) {
		return Restrictions.isEmpty(propertyName);
	}

	/**
	 * Constrain a collection valued property to be non-empty
	 */
	public static Criterion isNotEmpty(String propertyName) {
		return Restrictions.isNotEmpty(propertyName);
	}

	/**
	 * Consider using any of the natural id based loading stuff from session
	 * instead, especially in cases where the restriction is the full set of
	 * natural id values.
	 * 
	 * @see Session#byNaturalId(Class)
	 * @see Session#byNaturalId(String)
	 * @see Session#bySimpleNaturalId(Class)
	 * @see Session#bySimpleNaturalId(String)
	 */
	public static NaturalIdentifier naturalId() {
		return new NaturalIdentifier();
	}

}
