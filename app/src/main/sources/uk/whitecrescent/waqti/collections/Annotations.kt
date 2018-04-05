package uk.whitecrescent.waqti.collections

/**
 * Used primarily in abstract classes to indicate that it is recommended that the concrete class that will extend
 * this abstract class override this function or property with its own implementation since the default
 * implementation may not create the desired functionality and is instead with a default implementation for
 * convenience purposes and not necessarily for effectiveness.
 *
 * This should not be used on functions or properties where not overriding them will allow for a reasonable
 * implementation.
 *
 * The details of why it is recommended to override and the consequences if not overriden should be documented clearly.
 *
 * @author Bassam Helal
 */
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
annotation class OverrideRecommended

/**
 * Used to indicate that is either not recommended or not necessary (or both) to override the function, property,
 * constructor or class (even though it is allowed). This would usually be because the current implementation should
 * be sufficient for most applications and therefore an override (without calling super) cannot guarantee a desired
 * outcome.
 *
 * The details of why it is not recommended to override and what actions to take when needed to override should be
 * documented clearly.
 *
 * @author Bassam Helal
 */
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.CONSTRUCTOR)
annotation class NoOverride