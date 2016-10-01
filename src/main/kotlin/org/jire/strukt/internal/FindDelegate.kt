/*
 * Copyright 2016 Thomas Nappo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jire.strukt.internal

import kotlin.reflect.KClass
import kotlin.reflect.declaredMemberProperties
import kotlin.reflect.jvm.javaField

inline fun <reified T : Any, DELEGATE : Any> findDelegate(
		instance: T, delegatingTo: KClass<DELEGATE>): DELEGATE? {

	for (prop in T::class.declaredMemberProperties) {
		val javaField = prop.javaField ?: continue

		if (delegatingTo.java.isAssignableFrom(javaField.type)) {
			javaField.isAccessible = true

			@Suppress("UNCHECKED_CAST")
			return javaField.get(instance) as DELEGATE
		}
	}

	return null
}