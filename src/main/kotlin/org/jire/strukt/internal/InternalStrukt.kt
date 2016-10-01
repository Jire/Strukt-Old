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

import it.unimi.dsi.fastutil.objects.ObjectArrayList
import org.jire.strukt.Strukt
import org.jire.strukt.StruktPointer
import org.jire.strukt.internal.StruktData.struktToInternal
import org.jire.strukt.member.Member
import kotlin.reflect.KProperty1
import kotlin.reflect.declaredMemberProperties

class InternalStrukt(val strukt: Strukt, val props: Collection<KProperty1<Any, Any>>) {

	inner class StruktPointerContainer(var pointer: StruktPointer)

	private val pointerContainer: ThreadLocal<StruktPointerContainer>
			= ThreadLocal.withInitial { StruktPointerContainer(-1) }

	var pointer: StruktPointer
		get() = pointerContainer.get().pointer
		set(value) {
			pointerContainer.get().pointer = value
		}

	val members = ObjectArrayList<Member>(props.size)

}

inline val <reified T : Strukt> T.strukt: InternalStrukt
	get() {
		val struktToInternal = struktToInternal.get()
		if (struktToInternal.containsKey(this)) return struktToInternal[this]!!

		val props = T::class.declaredMemberProperties

		@Suppress("UNCHECKED_CAST")
		val internal = InternalStrukt(this, props as Collection<KProperty1<Any, Any>>)
		for (prop in props) {
			val member = findDelegate(this, Member::class) ?: continue
			internal.members.add(member)
		}
		struktToInternal.put(this, internal)

		return internal
	}