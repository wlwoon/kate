package com.wlwoon.kate

import java.util.*

class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        var len = 0
        var set = HashSet<Char>()
        val sLen = s.length
        var leftIndex = 0
        var rightIndex = sLen-1
        var newStr = s

        do {
            val leftChar = s[leftIndex]
            val rightChar = s[rightIndex]
            if (set.contains(leftChar)) newStr = newStr.substring(leftIndex,rightIndex+1)
            if (set.contains(rightChar)) newStr = newStr.substring(0,rightIndex--)
            set.add(leftChar)
            set.add(rightChar)
            leftIndex++
        }while (leftIndex==rightIndex)
        len = newStr.length
        println("无重复字符的最长子串是：$newStr")
        return len
    }
}

fun main() {
    val lengthOfLongestSubstring = Solution().lengthOfLongestSubstring("abcbcsaa")
    println(
        "长度是：$lengthOfLongestSubstring"
    )
}

