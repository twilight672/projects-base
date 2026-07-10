/**
 * 书籍封面占位图生成工具
 * 
 * 当书籍没有封面图片时，用这个工具生成一个基于书名的占位图
 * 同一本书每次都会生成相同的颜色，保证视觉一致性
 */

/**
 * 根据字符串计算哈希值
 * 用于根据书名生成稳定的颜色索引
 * 
 * @param {string} str - 输入字符串（通常是书名）
 * @returns {number} 哈希值（非负整数）
 */
const hashCode = (str) => {
  let hash = 0
  for (let i = 0; i < str.length; i++) {
    const char = str.charCodeAt(i)
    // 经典的 djb2 哈希算法变体
    hash = ((hash << 5) - hash) + char
    hash = hash & hash // 转换为32位整数
  }
  return Math.abs(hash)
}

/**
 * 根据书名选取一个颜色
 * 同一本书名总是返回相同的颜色
 * 
 * @param {string} title - 书名
 * @returns {string} 十六进制颜色值，如 '#4A90A4'
 */
const getColorFromTitle = (title) => {
  // 预定义的柔和色调，适合作为书籍封面背景
  const colors = [
    '#4A90A4', '#6B8E8E', '#8B7B8B', '#A4786B',
    '#7B8B6B', '#8B6B7B', '#6B7B8B', '#A68B6B',
    '#7B6B8B', '#8B8B6B', '#6B8B7B', '#8B6B6B',
    '#6B6B8B', '#8B7B6B', '#7B8B8B', '#6B7B6B'
  ]
  // 用哈希值对颜色数组长度取模，得到一个稳定的颜色索引
  return colors[hashCode(title) % colors.length]
}

/**
 * 生成书籍封面占位图
 * 
 * 使用内联 SVG 生成图片，无需依赖外部图片资源
 * 生成的图片会显示书名首字母和完整书名
 * 
 * @param {string} title - 书名，用于生成颜色和显示文字
 * @param {number} width - 图片宽度，默认 300
 * @param {number} height - 图片高度，默认 400
 * @returns {string} Data URL 格式的图片，可直接用于 img 标签的 src 属性
 * 
 * @example
 * <img :src="getPlaceholderCover(book.title)" />
 */
export const getPlaceholderCover = (title, width = 300, height = 400) => {
  const bgColor = getColorFromTitle(title || '书')
  const initial = (title || '书').charAt(0)
  
  // 构建 SVG 图片
  const svg = `
    <svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}">
      <!-- 背景色块 -->
      <rect width="100%" height="100%" fill="${bgColor}"/>
      <!-- 内部装饰边框 -->
      <rect x="10" y="10" width="${width - 20}" height="${height - 20}" fill="rgba(255,255,255,0.1)" rx="5"/>
      <!-- 书名首字母，居中大字显示 -->
      <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle"
            fill="rgba(255,255,255,0.9)" font-family="Arial Black, sans-serif" font-size="${Math.min(width, height) / 3}">
        ${initial}
      </text>
      <!-- 完整书名，底部小字显示 -->
      <text x="50%" y="65%" dominant-baseline="middle" text-anchor="middle"
            fill="rgba(255,255,255,0.6)" font-family="Arial, sans-serif" font-size="12">
        ${title || '书籍'}
      </text>
    </svg>
  `
  // 转换为 Data URL，可直接用于 img src
  return `data:image/svg+xml;charset=utf-8,${encodeURIComponent(svg.trim())}`
}

export default getPlaceholderCover