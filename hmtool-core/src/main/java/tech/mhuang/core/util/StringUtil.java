package tech.mhuang.core.util;

import java.util.Iterator;


/**
 * 字符串工具类
 *
 * @author mhuang
 * @version 1.0.0
 * @since JDK1.8
 */
public class StringUtil {

    /**
     * 空字符串标识
     *
     * @date 2018-10-10 10:20
     */
    private static final String EMPTY = "";

    /**
     * 未找到索引的常量
     */
    private static final int INDEX_NOT_FOUND = -1;

    private StringUtil() {

    }

    /**
     * 判断字符串是否是空
     *
     * @param cs 判断的字符串
     * @return boolean 应答true为空|false不为空
     */

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断字符串是否是空、并且判断字符串若是"null"也为空
     *
     * @param cs 判断的字符串
     * @return boolean 应答true为空|false不为空
     */
    public static boolean isEmptyNull(CharSequence cs) {
        return cs == null || cs.length() == 0 || "null".equals(cs) || "NULL".equals(cs);
    }


    /**
     * 判断字符串是否不为空、并且判断字符串若是"null"也为空
     *
     * @param cs 判断的字符串
     * @return boolean 应答true不为空|false为空
     */
    public static boolean isNotEmptyNull(CharSequence cs) {

        return !isEmptyNull(cs);
    }

    /**
     * 判断字符串是否不是空
     *
     * @param cs 判断的字符串
     * @return boolean 应答true不为空|false为空
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断字符串是否为空、判断过程中过滤空格换行符
     *
     * @param cs 判断的字符串
     * @return boolean 应答true为空|false不为空
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否不为空、判断过程中过滤空格换行符
     *
     * @param cs 判断的字符串
     * @return 应答True不为空|False为空
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 将迭代器根据规则拆分后拼接成一个字符串，并以cs参数隔开
     *
     * @param iterable 迭代器
     * @param cs       拼接的参数
     * @return 返回拼接的字符串
     * <p><strong>特殊情况:</strong></p>
     * <p>1:若迭代器为空则返回null</p>
     * <p>2:若迭代器存放的是数据为空则返回null字符串拼接方式</p>
     * <p>3:若cs为空、则不加入分隔符</p>
     */
    public static String join(final Iterable<?> iterable, final CharSequence cs) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), cs);
    }


    /**
     * 将迭代器根据规则拆分后拼接成一个字符串，并以cs参数隔开
     *
     * @param iterator 迭代器
     * @param cs       拼接的参数
     * @return 返回的拼接的字符串
     *
     * <p>特殊情况</p>
     * <p>1:若迭代器为空则返回null</p>
     * <p>2:若迭代器存放的是数据为空则返回null字符串拼接方式</p>
     * <p>3:若cs为空、则不加入分隔符</p>
     *
     * <pre>
     *     <b>示例：</b>
     *      StringUtil.join(null, *)                = null
     *      StringUtil.join([], *)                  = ""
     *      StringUtil.join([null], *)              = "null"
     *      StringUtil.join(["a", "b", "c"], "--")  = "a--b--c"
     *      StringUtil.join(["a", "b", "c"], null)  = "abc"
     *      StringUtil.join(["a", "b", "c"], "")    = "abc"
     *      StringUtil.join([null, "", "a"], ',')   = "null,null,a"
     *   </pre>
     */
    public static String join(final Iterator<?> iterator, final CharSequence cs) {
        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            final String result = String.valueOf(first);
            return result;
        }

        // two or more elements
        // Java default is 16, probably too small
        final StringBuilder buf = new StringBuilder(256);
        if (first != null && first != "") {
            buf.append(first);
        } else {
            buf.append("null");
        }

        while (iterator.hasNext()) {
            if (cs != null) {
                buf.append(cs);
            }
            final Object obj = iterator.next();
            if (obj != null && obj != "") {
                buf.append(obj);
            } else {
                buf.append("null");
            }
        }
        return buf.toString();
    }

    /**
     * 根据字符串截取最后出现的字符串之前
     *
     * @param str       字符串
     * @param separator 截取的字符串
     * @return String 返回截取后的字符串
     * <pre>
     *         <b>示例：</b>
     *          StringUtil.substringBeforeLast(null, *)      = null
     *          StringUtil.substringBeforeLast("", *)        = ""
     *          StringUtil.substringBeforeLast("abcba", "b") = "abc"
     *          StringUtil.substringBeforeLast("abc", "c")   = "ab"
     *          StringUtil.substringBeforeLast("a", "a")     = ""
     *          StringUtil.substringBeforeLast("a", "z")     = "a"
     *          StringUtil.substringBeforeLast("a", null)    = "a"
     *          StringUtil.substringBeforeLast("a", "")      = "a"
     *     </pre>
     */
    public static String substringBeforeLast(final String str, final String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        final int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * <p>按照顺序比较两个字符串{@link String#compareTo(String)}
     *
     * @param str1 要比较的字符串
     * @param str2 要比较的字符串
     * @return &lt;0, 0, &gt;0
     * , returning :</p>
     * <ul>
     * <li>{@code int = 0}, 如果 {@code str1} 等于 {@code str2} (或两者都为 {@code null})</li>
     * <li>{@code int < 0}, 如果 {@code str1} 小于 {@code str2}</li>
     * <li>{@code int > 0}, 如果 {@code str1} 大于{@code str2}</li>
     * </ul>
     * <pre>
     *  <b>示例：</b>
     *  StringUtil.compare(null, null)   = 0
     *  StringUtil.compare(null , "a")   &lt; 0
     *  StringUtil.compare("a", null)    &gt; 0
     *  StringUtil.compare("abc", "abc") = 0
     *  StringUtil.compare("a", "b")     &lt; 0
     *  StringUtil.compare("b", "a")     &gt; 0
     *  StringUtil.compare("a", "B")     &gt; 0
     *  StringUtil.compare("ab", "abc")  &lt; 0
     * </pre>
     * @see String#compareTo(String)
     */
    public static int compare(final String str1, final String str2) {
        return compare(str1, str2, true);
    }

    /**
     * <p>按照顺序比较两个字符串{@link String#compareTo(String)}
     *
     * @param str1       要比较的字符串
     * @param str2       要比较的字符串
     * @param nullIsLess 是否认为{@code null}值小于非{@code null}值
     * @return &lt;0, 0, &gt;0
     * , returning :</p>
     * <ul>
     * <li>{@code int = 0}, 如果 {@code str1} 等于 {@code str2} (或两者都为 {@code null})</li>
     * <li>{@code int < 0}, 如果 {@code str1} 小于 {@code str2}</li>
     * <li>{@code int > 0}, 如果 {@code str1} 大于{@code str2}</li>
     * </ul>
     * <pre>
     *  <b>示例：</b>
     * StringUtil.compare(null, null, *)     = 0
     * StringUtil.compare(null , "a", true)  &lt; 0
     * StringUtil.compare(null , "a", false) &gt; 0
     * StringUtil.compare("a", null, true)   &gt; 0
     * StringUtil.compare("a", null, false)  &lt; 0
     * StringUtil.compare("abc", "abc", *)   = 0
     * StringUtil.compare("a", "b", *)       &lt; 0
     * StringUtil.compare("b", "a", *)       &gt; 0
     * StringUtil.compare("a", "B", *)       &gt; 0
     * StringUtil.compare("ab", "abc", *)    &lt; 0
     * </pre>
     * @see String#compareTo(String)
     */
    public static int compare(final String str1, final String str2, final boolean nullIsLess) {
        if (str1 == str2) {
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return str1.compareTo(str2);
    }

    /**
     * <p>删除空格</p>
     *
     * @param str 要删除空格的字符串、可以为空
     * @return String 返回删除空格后的字符串
     * <pre>
     *     <b>示例：</b>
     *          StringUtil.trim(null)          = null
     *          StringUtil.trim("")            = ""
     *          StringUtil.trim("     ")       = ""
     *          StringUtil.trim("abc")         = "abc"
     *          StringUtil.trim("    abc    ") = "abc"
     * </pre>
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    /**
     * <p>返回指定子字符串第一次出现的字符串中的索引</p>
     *
     * @param seq       查找的字符串
     * @param searchSeq 查询字符串的索引
     * @return int 出现的索引
     * <pre>
     *     <b>示例：</b>
     *          StringUtil.indexOf(null,*)     = -1
     *          StringUtil.indexOf(*,null)     = -1
     *          StringUtil.indexOf("","")      = 0
     *          StringUtil.indexOf("abca","a") = 0
     *          StringUtil.indexOf("abca","c") = 2
     * </pre>
     * @see String#indexOf(String)
     */
    public static int indexOf(final CharSequence seq, final CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return INDEX_NOT_FOUND;
        }
        return seq.toString().indexOf(searchSeq.toString());
    }

    /**
     * 将字符串分裂成字符串数组.
     *
     * @param seq      需要分裂的字符串
     * @param splitSeq 分裂的标识
     * @return 数组
     */
    public static String[] split(final CharSequence seq, final CharSequence splitSeq) {
        if (StringUtil.isEmpty(seq)) {
            return null;
        }
        return seq.toString().split(splitSeq.toString());
    }

    /**
     * 判断2个字符对象是否相等
     *
     * @param cs1 第一个字符对象
     * @param cs2 第二个字符对象
     * @return 相等返回True 不相等返回False
     */
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return cs1.equals(cs2);
    }

    /**
     * 首字母转大写
     *
     * @param s 转换的字符串
     * @return 返回首字母变大写的字符串
     */
    public static String toUpperCaseFirstOne(String s) {
        char c = s.charAt(0);
        if (StringUtil.isEmpty(s) || Character.isUpperCase(c)) {
            return s;
        }
        return new StringBuilder().append(Character.toUpperCase(c)).append(s.substring(1)).toString();
    }

    /**
     * 忽略大小写判断查找的是否在字符对象理
     *
     * @param str       字符对象
     * @param searchStr 查找的字符对象
     * @return 是否包含忽略大小写的字符串，True包含 False不包含
     */
    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        final int len = searchStr.length();
        final int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (CharSequenceUtil.regionMatches(str, true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取字符对象的长度
     *
     * @param cs 字符对象
     * @return 字符对象长度
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * 截取字符串分离之后的字符串
     *
     * @param str       字符串
     * @param separator 分离的字符串
     * @return 分离后的字符串
     */
    public static String substringAfter(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }
}
