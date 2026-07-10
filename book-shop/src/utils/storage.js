/**
 * 本地存储工具模块
 * 
 * 用 localStorage 存储用户的购物车、订单、收藏、评论等数据
 * 每个用户的数据通过用户名区分，登录后切换账号会自动切换数据
 */

// ==================== 购物车相关 ====================

/**
 * 生成当前用户的购物车存储键
 * 不同用户的购物车数据相互独立，未登录用户使用 'guest' 作为标识
 */
const getCartKey = () => {
  const userName = localStorage.getItem('userName') || 'guest'
  return `cart_${userName}`
}

/**
 * 获取当前用户的购物车列表
 * @returns {Array} 购物车商品数组，每项包含书籍信息和数量
 */
export const getCart = () => {
  const cart = localStorage.getItem(getCartKey());
  return cart ? JSON.parse(cart) : [];
};

/**
 * 添加商品到购物车
 * 如果商品已存在，数量加1；否则新增一条记录
 * @param {Object} book - 要添加的书籍对象
 */
export const addToCart = (book) => {
  const cart = getCart();
  const existBook = cart.find(item => item.id === book.id);
  if (existBook) {
    // 已有该商品，数量+1
    existBook.quantity += 1;
  } else {
    // 新商品，初始化数量为1
    cart.push({ ...book, quantity: 1 });
  }
  localStorage.setItem(getCartKey(), JSON.stringify(cart));
};

/**
 * 修改购物车中某个商品的数量
 * @param {string|number} bookId - 书籍ID
 * @param {number} quantity - 新的数量值
 */
export const updateCartQuantity = (bookId, quantity) => {
  const cart = getCart();
  const book = cart.find(item => item.id === bookId);
  if (book) {
    book.quantity = quantity;
    localStorage.setItem(getCartKey(), JSON.stringify(cart));
  }
};

/**
 * 从购物车删除指定商品
 * @param {string|number} bookId - 要删除的书籍ID
 */
export const removeFromCart = (bookId) => {
  let cart = getCart();
  cart = cart.filter(item => item.id !== bookId);
  localStorage.setItem(getCartKey(), JSON.stringify(cart));
};

/**
 * 清空当前用户的购物车
 * 下单成功后调用此方法
 */
export const clearCart = () => {
  localStorage.setItem(getCartKey(), JSON.stringify([]));
};

// ==================== 订单相关 ====================

/**
 * 生成当前用户的订单存储键
 * @returns {string} 格式为 'orders_用户名'
 */
export const getOrdersKey = () => {
  const userName = localStorage.getItem('userName') || 'guest'
  return `orders_${userName}`
}

/**
 * 获取当前用户的所有订单
 * @returns {Array} 订单数组，按创建时间排序
 */
export const getOrders = () => {
  const key = getOrdersKey()
  const orders = localStorage.getItem(key)
  return orders ? JSON.parse(orders) : []
};

/**
 * 创建新订单
 * 自动生成订单号和创建时间
 * @param {Object} order - 订单基础信息（商品列表、总价、收货地址等）
 * @returns {Object} 创建完成的订单对象，包含订单号和创建时间
 */
export const createOrder = (order) => {
  const orders = getOrders();
  // 订单号 = 时间戳 + 3位随机数，保证唯一性
  const orderNo = Date.now() + '' + Math.floor(Math.random() * 1000);
  const newOrder = { ...order, orderNo, createTime: new Date().getTime() };
  orders.push(newOrder);
  localStorage.setItem(getOrdersKey(), JSON.stringify(orders));
  return newOrder;
};

/**
 * 更新订单状态
 * @param {string} orderNo - 订单号
 * @param {string} status - 新状态（如 'paid'、'shipped'、'completed'、'cancelled'）
 * @returns {Object|null} 更新后的订单对象，找不到订单时返回 null
 */
export const updateOrderStatus = (orderNo, status) => {
  const orders = getOrders();
  const index = orders.findIndex(item => item.orderNo === orderNo);
  if (index !== -1) {
    orders[index].status = status;
    localStorage.setItem(getOrdersKey(), JSON.stringify(orders));
    return orders[index];
  }
  return null;
};

/**
 * 取消订单
 * 实际上是将订单状态改为 'cancelled'
 * @param {string} orderNo - 要取消的订单号
 * @returns {Object|null} 取消后的订单对象
 */
export const cancelOrder = (orderNo) => {
  return updateOrderStatus(orderNo, 'cancelled');
};

// ==================== 收藏相关 ====================

/**
 * 获取当前登录的用户名
 * 未登录时返回 'guest'
 * @returns {string} 用户名
 */
const getCurrentUserName = () => {
  return localStorage.getItem('userName') || 'guest'
}

/**
 * 生成当前用户的收藏存储键
 * @returns {string} 格式为 'favorites_用户名'
 */
const getFavoritesKey = () => {
  const userName = getCurrentUserName()
  return `favorites_${userName}`
}

/**
 * 获取当前用户收藏的书籍ID列表
 * @returns {Array<string|number>} 书籍ID数组
 */
export const getFavorites = () => {
  const key = getFavoritesKey()
  const favorites = localStorage.getItem(key);
  return favorites ? JSON.parse(favorites) : [];
};

/**
 * 添加书籍到收藏夹
 * 重复收藏会自动忽略
 * @param {string|number} bookId - 书籍ID
 */
export const addFavorite = (bookId) => {
  const key = getFavoritesKey()
  const favorites = getFavorites();
  if (!favorites.includes(bookId)) {
    favorites.push(bookId);
    localStorage.setItem(key, JSON.stringify(favorites));
  }
};

/**
 * 从收藏夹移除书籍
 * @param {string|number} bookId - 要取消收藏的书籍ID
 */
export const removeFavorite = (bookId) => {
  const key = getFavoritesKey()
  let favorites = getFavorites();
  favorites = favorites.filter(id => id !== bookId);
  localStorage.setItem(key, JSON.stringify(favorites));
};

/**
 * 检查某本书是否已被收藏
 * @param {string|number} bookId - 书籍ID
 * @returns {boolean} 已收藏返回 true
 */
export const isFavorite = (bookId) => {
  const favorites = getFavorites();
  return favorites.includes(bookId);
};

// ==================== 评论相关 ====================

/**
 * 获取某本书的所有评论
 * 注意：评论数据是全局共享的，不区分用户
 * @param {string|number} bookId - 书籍ID
 * @returns {Array} 该书籍的评论列表
 */
export const getCommentsByBookId = (bookId) => {
  const comments = localStorage.getItem('comments');
  const commentList = comments ? JSON.parse(comments) : [];
  return commentList.filter(comment => comment.bookId === bookId);
};

/**
 * 发表新评论
 * 自动添加评论ID和创建时间
 * @param {Object} comment - 评论内容，包含 bookId、userName、content、rating 等
 */
export const addComment = (comment) => {
  const comments = localStorage.getItem('comments');
  const commentList = comments ? JSON.parse(comments) : [];
  commentList.push({ ...comment, id: Date.now(), createTime: new Date().getTime(), 
   createdAt: new Date().toISOString()
   });
  localStorage.setItem('comments', JSON.stringify(commentList));
};

/**
 * 删除评论
 * @param {string|number} commentId - 要删除的评论ID
 */
export const deleteComment = (commentId) => {
  let comments = localStorage.getItem('comments');
  let commentList = comments ? JSON.parse(comments) : [];
  commentList = commentList.filter(comment => comment.id !== commentId);
  localStorage.setItem('comments', JSON.stringify(commentList));
};

/**
 * 获取某个用户发表的所有评论
 * 用于"我的评论"页面
 * @param {string} userName - 用户名
 * @returns {Array} 该用户的评论列表
 */
export const getMyComments = (userName) => {
  const comments = localStorage.getItem('comments');
  const commentList = comments ? JSON.parse(comments) : [];
  return commentList.filter(comment => comment.userName === userName);
};

// ==================== 用户登录相关 ====================

/**
 * 检查用户是否已登录
 * @returns {boolean} 已登录返回 true
 */
export const isLoggedIn = () => {
  return localStorage.getItem('isLogin') === 'true';
};

/**
 * 获取当前登录用户的基本信息
 * @returns {Object} 包含 userName 和 isLogin 两个字段
 */
export const getCurrentUser = () => {
  return {
    userName: localStorage.getItem('userName') || '',
    isLogin: isLoggedIn()
  };
};

/**
 * 用户登录验证
 * @param {string} userName - 用户名
 * @param {string} password - 密码
 * @returns {Object} 登录结果，success 表示是否成功，message 表示失败原因
 */
export const login = (userName, password) => {
  const users = getUsers();
  
  const user = users.find(u => u.userName === userName);
  
  if (!user) {
    return { success: false, message: '用户名不存在' };
  }
  
  if (user.password !== password) {
    return { success: false, message: '密码错误' };
  }
  
  // 登录成功，记录登录状态
  localStorage.setItem('isLogin', 'true');
  localStorage.setItem('userName', userName);
  return { success: true };
};

/**
 * 用户登出
 * 清除登录状态，但保留用户名以便下次登录
 */
export const logout = () => {
  localStorage.setItem('isLogin', 'false');
  localStorage.removeItem('userName');
};

/**
 * 用户注册
 * @param {string} userName - 用户名
 * @param {string} password - 密码
 * @param {string} email - 邮箱
 * @returns {Object} 注册结果，success 表示是否成功，message 表示失败原因
 */
export const register = (userName, password, email) => {
  const users = getUsers();
  
  // 检查用户名是否已存在
  if (users.find(user => user.userName === userName)) {
    return { success: false, message: '用户名已存在' };
  }
  
  // 检查邮箱是否已被注册
  if (users.find(user => user.email === email)) {
    return { success: false, message: '邮箱已被注册' };
  }
  
  // 创建新用户
  const newUser = {
    id: Date.now().toString(),
    userName,
    password,
    email,
    createdAt: new Date().getTime()
  };
  
  users.push(newUser);
  localStorage.setItem('users', JSON.stringify(users));
  
  return { success: true };
};

/**
 * 获取所有注册用户列表
 * @returns {Array} 用户对象数组
 */
export const getUsers = () => {
  const users = localStorage.getItem('users');
  return users ? JSON.parse(users) : [];
};

// ==================== 管理员相关 ====================

/**
 * 初始化默认管理员账号
 * 如果系统中还没有 admin 账号，就自动创建一个
 * 默认账号: admin / admin123
 */
const initAdmin = () => {
  const admins = localStorage.getItem('admins');
  const adminList = admins ? JSON.parse(admins) : [];
  if (!adminList.find(a => a.userName === 'admin')) {
    adminList.push({
      id: '1',
      userName: 'admin',
      password: 'admin123',
      createdAt: new Date().getTime()
    });
    localStorage.setItem('admins', JSON.stringify(adminList));
  }
};

/**
 * 获取所有管理员列表
 * 会自动初始化默认管理员账号
 * @returns {Array} 管理员对象数组
 */
export const getAdmins = () => {
  initAdmin();
  const admins = localStorage.getItem('admins');
  return admins ? JSON.parse(admins) : [];
};

/**
 * 管理员登录验证
 * @param {string} userName - 管理员用户名
 * @param {string} password - 管理员密码
 * @returns {Object} 登录结果，success 表示是否成功，message 表示失败原因
 */
export const adminLogin = (userName, password) => {
  const admins = getAdmins();
  const admin = admins.find(a => a.userName === userName && a.password === password);
  
  if (!admin) {
    return { success: false, message: '管理员账号或密码错误' };
  }
  
  // 记录管理员登录状态
  localStorage.setItem('isAdmin', 'true');
  localStorage.setItem('adminName', userName);
  return { success: true };
};

/**
 * 管理员登出
 */
export const adminLogout = () => {
  localStorage.setItem('isAdmin', 'false');
  localStorage.removeItem('adminName');
};

/**
 * 检查是否处于管理员登录状态
 * @returns {boolean} 已登录返回 true
 */
export const isAdminLoggedIn = () => {
  return localStorage.getItem('isAdmin') === 'true';
};

/**
 * 获取当前管理员信息
 * @returns {Object} 包含 userName 和 isAdmin 两个字段
 */
export const getCurrentAdmin = () => {
  return {
    userName: localStorage.getItem('adminName') || '',
    isAdmin: isAdminLoggedIn()
  };
};

/**
 * 将普通用户提升为管理员
 * 需要验证用户身份，只有已注册用户才能成为管理员
 * @param {string} userName - 要提升的用户名
 * @param {string} password - 用户密码（用于身份验证）
 * @returns {Object} 操作结果
 */
export const addAdmin = (userName, password) => {
  const users = getUsers();
  const admins = getAdmins();
  
  // 先验证用户身份
  const user = users.find(u => u.userName === userName && u.password === password);
  if (!user) {
    return { success: false, message: '用户不存在或密码错误' };
  }
  
  // 检查是否已经是管理员
  if (admins.find(a => a.userName === userName)) {
    return { success: false, message: '该用户已是管理员' };
  }
  
  // 添加为管理员
  admins.push({
    id: Date.now().toString(),
    userName,
    password,
    createdAt: new Date().getTime()
  });
  localStorage.setItem('admins', JSON.stringify(admins));
  return { success: true };
};

/**
 * 删除管理员账号
 * 注意：默认的 admin 账号不能被删除，确保系统始终有管理员入口
 * @param {string} userName - 要删除的管理员用户名
 * @returns {Object} 操作结果
 */
export const removeAdmin = (userName) => {
  // 保护默认管理员账号
  if (userName === 'admin') {
    return { success: false, message: '不能删除默认管理员' };
  }
  
  let admins = getAdmins();
  const index = admins.findIndex(a => a.userName === userName);
  if (index === -1) {
    return { success: false, message: '管理员不存在' };
  }
  
  admins.splice(index, 1);
  localStorage.setItem('admins', JSON.stringify(admins));
  return { success: true };
};
