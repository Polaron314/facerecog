package com.sacredheartkingston.jtowner.facerecog;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utils {

	public static double sigmoid(double i) {
		return 1 / (1 + Math.pow(Math.E, -1 * i));
	}

	public static double random(double min, double max) {
		double random = Math.random();
		random *= (max - min);
		random += min;
		return random;
	}

	public static int[] getByte(int n) {
		int[] binaryArray = new int[8];
		String binaryString = Integer.toBinaryString(n);
		for (int i = binaryString.length() - 1; i > -1; i--) {
			binaryArray[7 - (binaryString.length() - 1 - i)] = Character.getNumericValue(binaryString.charAt(i));
		}
		return binaryArray;
	}

	public static int[] getTwoBytes(int n) {
		int[] binaryArray = new int[16];
		String binaryString = Integer.toBinaryString(n);
		for (int i = binaryString.length() - 1; i > -1; i--) {
			binaryArray[15 - (binaryString.length() - 1 - i)] = Character.getNumericValue(binaryString.charAt(i));
		}
		return binaryArray;
	}

	public static int[] getFourBytes(long n) {
		int[] binaryArray = new int[32];
		String binaryString = Long.toBinaryString(n);
		for (int i = binaryString.length() - 1; i > -1; i--) {
			binaryArray[31 - (binaryString.length() - 1 - i)] = Character.getNumericValue(binaryString.charAt(i));
		}
		return binaryArray;
	}

	public static int binaryToInt(int[] binary) {
		String binaryString = "";
		for (int i = 0; i < binary.length; i++) {
			binaryString += binary[i] + "";
		}
		int binaryInt = Integer.parseInt(binaryString, 2);
		return binaryInt;
	}

	public static long binaryToLong(int[] binary) {
		String binaryString = "";
		for (int i = 0; i < binary.length; i++) {
			binaryString += binary[i] + "";
		}
		if (binaryString != "") {
			long binaryInt = Long.parseLong(binaryString, 2);
			return binaryInt;
		} else {
			return 0;
		}

	}

	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	public static int[] concatAll(int[] typeBinary, int[] idBinary, int[] outputBinary, int[] weightsBinary) {
		Integer[] typeBinaryO = Utils.toObject(typeBinary);
		Integer[] idBinaryO = Utils.toObject(idBinary);
		Integer[] outputBinaryO = Utils.toObject(outputBinary);
		Integer[] weightsBinaryO = Utils.toObject(weightsBinary);
		Integer[] total = Utils.concatAll(typeBinaryO, idBinaryO, outputBinaryO, weightsBinaryO);
		return Utils.toPrimitive(total);
	}

	private static Integer[] toObject(int[] intArray) {

		Integer[] result = new Integer[intArray.length];
		for (int i = 0; i < intArray.length; i++) {
			result[i] = Integer.valueOf(intArray[i]);
		}
		return result;
	}

	private static int[] toPrimitive(Integer[] IntegerArray) {

		int[] result = new int[IntegerArray.length];
		for (int i = 0; i < IntegerArray.length; i++) {
			result[i] = IntegerArray[i].intValue();
		}
		return result;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

}
