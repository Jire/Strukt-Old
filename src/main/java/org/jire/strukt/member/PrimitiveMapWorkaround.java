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

package org.jire.strukt.member;

import it.unimi.dsi.fastutil.longs.*;
import org.jire.strukt.Strukt;

public final class PrimitiveMapWorkaround {

	private PrimitiveMapWorkaround() {
	}

	public static void put(Long2BooleanArrayMap map, long key, boolean value) {
		map.put(key, value);
	}

	public static void put(Long2ByteArrayMap map, long key, byte value) {
		map.put(key, value);
	}

	public static void put(Long2ShortArrayMap map, long key, short value) {
		map.put(key, value);
	}

	public static void put(Long2CharArrayMap map, long key, char value) {
		map.put(key, value);
	}

	public static void put(Long2IntArrayMap map, long key, int value) {
		map.put(key, value);
	}

	public static void put(Long2LongArrayMap map, long key, long value) {
		map.put(key, value);
	}

	public static void put(Long2FloatArrayMap map, long key, float value) {
		map.put(key, value);
	}

	public static void put(Long2DoubleArrayMap map, long key, double value) {
		map.put(key, value);
	}

	public static <T> void put(Long2ObjectArrayMap<T> map, long key, T value) {
		map.put(key, value);
	}

}
