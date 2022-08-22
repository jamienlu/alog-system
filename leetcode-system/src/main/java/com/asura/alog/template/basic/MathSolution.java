package com.asura.alog.template.basic;

import java.util.*;


public class MathSolution {
    /**
     * 寻找2数之和
     *
     * @param num 和
     * @param target 目标数组
     * @return 满足条件的下标
     */
    public int[] twoSum(int num, int[] target) {
        // 双指针
        if (target == null || target.length == 0) {
            return null;
        }
        Arrays.sort(target);
        int left = 0;
        int right = target.length - 1 ;
        while (left > right) {
            if (target[left] + target[right] > num) {
                right -= 1;
            } else if (target[left] + target[right] > num) {
                left += 1;
            } else {
                return new int[]{left,right};
            }
        }
        return null;
    }

    public int[] twoSumHash(int num, int[] target) {
        // hash
        if (target == null || target.length == 0) {
            return null;
        }
        Map<Integer,Integer> numIndex = new HashMap();
        for (int i = 0; i < target.length; i++) {
            if (numIndex.containsKey(target[i] - num)) {
                return new int[]{numIndex.get(target[i] - num), i};
            } else {
                numIndex.put(target[i], i);
            }
        }
        return null;
    }

    /**
     * 寻找3数之和  a + b + c = 0
     * 1.返回数值非索引
     * 2.由于顺序问题需要去重
     * 3.返回所有答案
     *
     * @param target 目标数组
     * @return 满足条件的下标
     */
    public List<List<Integer>> threeSum(int[] target) {
        // 双指针
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(target);
        for (int i = 0; i < target.length - 2 ; i++) {
            // 去重
            if (i > 0 && target[i] == target[i -1]) {
                continue;
            }
            int left = i +1;
            int right = target.length  -1;
            while (left < right) {
                if (target[i] + target[left] + target[right] < 0) {
                    left++;
                } else if (target[i] + target[left] + target[right] > 0) {
                    right--;
                } else {
                    result.add(Arrays.asList(target[i],target[left],target[right]));
                    // 去重
                    while (left < right && target[left] == target[left+1]) {
                        left++;
                    }
                    while (left < right && target[right] == target[right-1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
            }
        }
        return result;
    }

    /**
     * 寻找N数之和  a + b + c = 0
     * 1.返回数值非索引
     * 2.由于顺序问题需要去重
     * 3.返回所有答案
     *
     * @param target 目标数组
     * @return 满足条件的数
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        // 分治法 N数之和 towSum(threeSum())
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        findNSum(nums, target, 4, Arrays.asList(), result);
        return result;
    }

    public void findNSum(int[] target, int value, int count, List<Integer> temps, List<List<Integer>> result) {
        if (target.length < count || count < 2) {
            return;
        }
        if (count == 2) {
            int left = 0;
            int right = target.length  -1;
            while (left < right) {
                if (target[left] + target[right] == value) {
                    List<Integer> demos = new ArrayList<>(temps);
                    demos.addAll(Arrays.asList(target[left],target[right]));
                    result.add(demos);

                    // 去重
                    while (left < right && target[left] == target[left+1]) {
                        left++;
                    }
                    while (left < right && target[right] == target[right-1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
                else if (target[left] + target[right] < value) {
                    left++;
                }
                else  {
                    right--;
                }
            }
        } else {
            for (int i = 0; i < target.length; i++) {
                // 去重
                if (i == 0 || (i > 0 && target[i] != target[i -1])) {
                    List<Integer> demos = new ArrayList<>(temps);
                    demos.add(target[i]);
                    findNSum(Arrays.copyOfRange(target, i + 1, target.length), value - target[i],count -1, demos, result);
                }
            }
        }
    }

    /**
     * 寻找4数之和为0
     * 1.4个数在不同数组里
     * 2.4个数组长度相同
     *
     * @return 满足条件的个数
     */
    public int fourSumCount(int[] a, int[] b, int[] c, int[] d) {
        int result = 0;
        Map<Integer,Integer> temp = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (!temp.containsKey(a[i] + b[j])) {
                    temp.put(a[i] + b[j], 1);
                } else {
                    temp.put(a[i] + b[j], temp.get(a[i] + b[j]) + 1);
                }

            }
        }
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < d.length; j++) {
               if (temp.containsKey(-(c[i]+d[j]))) {
                   result += temp.get(-(c[i]+d[j]));
               }

            }
        }
        return result;
    }


    /**
     * 寻找3数之和 最接近目标值的值
     *
     * @param arr 目标数组
     * @param target 目标值
     * @return 满足条件的下标
     */
    public int threeSumClosest(int[] arr, int target) {
        if (arr.length < 3) {
            return -1;
        }
        Arrays.sort(arr);
        int res = arr[0] + arr[1] + arr[2];
        for (int i = 0; i < arr.length - 2; i++) {
            if (i > 0 && arr[i] == arr[i-1] ) {
                continue;
            }
            int left = i + 1;
            int right = arr.length -1;
            while (left < right) {
                int s = arr[i] + arr[left] + arr[right];
                if (s == target) {
                    return s;
                }
                if (Math.abs(s - target) < Math.abs(res - target)) {
                    res = s;
                }
                if (s < target) {
                    left++;
                } else if (s > target) {
                    right++;
                }
            }
        }
        return res;
    }

}
