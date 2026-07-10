/**
 * 书籍列表数据
 * 
 * 本文件提供书籍的简要信息，用于首页展示、分类列表、搜索结果等场景
 * 与 bookData.js 配合使用，后者提供书籍的完整详情信息
 * 
 * 分类体系共12个分类，ID对应关系：
 * 1-经典文学  2-名人传记  3-心理学著作  4-个人成长  5-历史文化  6-家庭幸福
 * 7-数学      8-英语      9-计算机      10-思想政治 11-自然科学 12-经管
 */

/**
 * 书籍列表
 * 
 * 每本书包含以下字段：
 * - id: 书籍唯一标识
 * - title: 书名
 * - author: 作者
 * - price: 价格（单位：元）
 * - cover: 封面图片地址
 * - categoryId: 所属分类ID
 * - description: 简要介绍
 * - sales: 销量数据
 */
export const BOOK_LIST = [
  // ========== 经典文学类（categoryId: 1）==========
  { id: 1, title: "百年孤独", author: "加西亚·马尔克斯", price: 56.8, cover: "https://picsum.photos/300/400?random=1", categoryId: 1, description: "《百年孤独》是魔幻现实主义文学的代表作，描写了布恩迪亚家族七代人的传奇故事。", sales: 1200 },
  { id: 2, title: "活着", author: "余华", price: 28.5, cover: "https://picsum.photos/300/400?random=2", categoryId: 1, description: "讲述福贵一生的坎坷与坚韧，用平淡的文字诠释生命的韧性。", sales: 1800 },
  { id: 3, title: "边城", author: "沈从文", price: 32.8, cover: "https://picsum.photos/300/400?random=3", categoryId: 1, description: "以湘西茶峒为背景，讲述翠翠的纯美爱情故事，勾勒田园牧歌式的乡土风情。", sales: 950 },
  { id: 4, title: "红楼梦", author: "曹雪芹", price: 68.0, cover: "https://picsum.photos/300/400?random=4", categoryId: 1, description: "中国古典四大名著之首，以贾史王薛四大家族兴衰为背景的爱情悲剧。", sales: 2100 },
  { id: 5, title: "平凡的世界", author: "路遥", price: 72.0, cover: "https://picsum.photos/300/400?random=5", categoryId: 1, description: "全景式展现上世纪七八十年代中国城乡社会生活，讲述孙少安、孙少平兄弟的奋斗故事。", sales: 1600 },
  { id: 6, title: "追风筝的人", author: "卡勒德·胡赛尼", price: 38.5, cover: "https://picsum.photos/300/400?random=6", categoryId: 1, description: "关于救赎与成长的文学佳作，探讨人性、背叛与善良。", sales: 1400 },

  // ========== 名人传记类（categoryId: 2）==========
  { id: 7, title: "苏东坡传", author: "林语堂", price: 36.9, cover: "https://picsum.photos/300/400?random=7", categoryId: 2, description: "还原宋代大文豪苏轼跌宕起伏的一生，展现其豁达乐观的人生态度。", sales: 890 },
  { id: 8, title: "乌合之众", author: "古斯塔夫·勒庞", price: 32.5, cover: "https://picsum.photos/300/400?random=8", categoryId: 2, description: "解析群体心理的经典著作，探讨群体行为的特征与成因。", sales: 980 },
  { id: 9, title: "杨绛传", author: "罗银胜", price: 34.0, cover: "https://picsum.photos/300/400?random=9", categoryId: 2, description: "记录杨绛先生百年人生，展现知识分子的风骨与智慧。", sales: 720 },
  { id: 10, title: "人类群星闪耀时", author: "斯蒂芬·茨威格", price: 29.8, cover: "https://picsum.photos/300/400?random=10", categoryId: 2, description: "选取历史上14个决定性瞬间，描写伟人的抉择与命运。", sales: 850 },
  { id: 11, title: "成为波伏瓦", author: "凯特·柯克帕特里克", price: 45.0, cover: "https://picsum.photos/300/400?random=11", categoryId: 2, description: "深度解读女权先驱波伏瓦的一生，展现独立女性的追寻之路。", sales: 620 },
  { id: 12, title: "拿破仑传", author: "艾米尔·路德维希", price: 39.5, cover: "https://picsum.photos/300/400?random=12", categoryId: 2, description: "客观还原拿破仑从平民到法兰西皇帝的传奇一生。", sales: 580 },

  // ========== 心理学著作类（categoryId: 3）==========
  { id: 13, title: "蛤蟆先生去看心理医生", author: "罗伯特·戴博德", price: 33.0, cover: "https://picsum.photos/300/400?random=13", categoryId: 3, description: "以童话形式讲述心理咨询全过程，帮助读者走出内耗、治愈心理困境。", sales: 1500 },
  { id: 14, title: "被讨厌的勇气", author: "岸见一郎", price: 31.8, cover: "https://picsum.photos/300/400?random=14", categoryId: 3, description: "基于阿德勒心理学，帮助读者摆脱讨好型人格、学会为自己而活。", sales: 1300 },
  { id: 15, title: "自卑与超越", author: "阿尔弗雷德·阿德勒", price: 28.0, cover: "https://picsum.photos/300/400?random=15", categoryId: 3, description: "心理学经典名著，解读自卑感的来源与影响，教会读者正视自卑、突破自我。", sales: 1100 },
  { id: 16, title: "心流", author: "米哈里·契克森米哈赖", price: 48.0, cover: "https://picsum.photos/300/400?random=16", categoryId: 3, description: "讲解如何在工作学习中进入专注忘我的状态，提升幸福感与做事效率。", sales: 920 },
  { id: 17, title: "当下的力量", author: "埃克哈特·托利", price: 35.0, cover: "https://picsum.photos/300/400?random=17", categoryId: 3, description: "引导人们放下对过去的悔恨、对未来的焦虑，专注活在当下。", sales: 880 },
  { id: 18, title: "津巴多普通心理学", author: "菲利普·津巴多", price: 89.0, cover: "https://picsum.photos/300/400?random=18", categoryId: 3, description: "全球经典心理学教材，内容全面、案例丰富，涵盖心理学全领域。", sales: 650 },

  // ========== 个人成长类（categoryId: 4）==========
  { id: 19, title: "原子习惯", author: "詹姆斯·克利尔", price: 45.8, cover: "https://picsum.photos/300/400?random=19", categoryId: 4, description: "讲解微小习惯的巨大力量，提供四步习惯养成法则，实现长期自我迭代。", sales: 1400 },
  { id: 20, title: "刻意练习", author: "安德斯·艾利克森", price: 36.5, cover: "https://picsum.photos/300/400?random=20", categoryId: 4, description: "打破天赋决定一切的误区，提出顶尖能力源于系统性刻意练习。", sales: 1200 },
  { id: 21, title: "自控力", author: "凯利·麦格尼格尔", price: 32.9, cover: "https://picsum.photos/300/400?random=21", categoryId: 4, description: "结合神经科学解析自控力原理，帮助读者提升自律能力、掌控生活节奏。", sales: 1150 },
  { id: 22, title: "少有人走的路", author: "M·斯科特·派克", price: 30.0, cover: "https://picsum.photos/300/400?random=22", categoryId: 4, description: "心智成长经典，教会读者直面痛苦、学会自律、收获成熟心智。", sales: 1050 },
  { id: 23, title: "你当像鸟飞往你的山", author: "塔拉·韦斯特弗", price: 37.0, cover: "https://picsum.photos/300/400?random=23", categoryId: 4, description: "真实自传体成长故事，诠释教育、勇气与自我重塑的力量。", sales: 980 },
  { id: 24, title: "也许你该找个人聊聊", author: "洛莉·戈特利布", price: 34.8, cover: "https://picsum.photos/300/400?random=24", categoryId: 4, description: "心理咨询师的回忆录，教会人们正视脆弱，拥抱不完美的自己。", sales: 860 },

  // ========== 历史文化类（categoryId: 5）==========
  { id: 25, title: "万历十五年", author: "黄仁宇", price: 35.5, cover: "https://picsum.photos/300/400?random=25", categoryId: 5, description: "以大历史观切入，剖析明朝制度弊病与王朝衰落的深层原因。", sales: 920 },
  { id: 26, title: "人类简史", author: "尤瓦尔·赫拉利", price: 58.0, cover: "https://picsum.photos/300/400?random=26", categoryId: 5, description: "从认知革命、农业革命到科学革命，梳理人类数百万年发展历程。", sales: 1300 },
  { id: 27, title: "明朝那些事儿", author: "当年明月", price: 128.0, cover: "https://picsum.photos/300/400?random=27", categoryId: 5, description: "以通俗幽默的方式讲述明朝三百年历史，让严肃历史变得生动有趣。", sales: 2000 },
  { id: 28, title: "中国通史", author: "吕思勉", price: 46.0, cover: "https://picsum.photos/300/400?random=28", categoryId: 5, description: "中国通史经典读本，条理清晰、史料扎实，系统了解中国历史的入门佳作。", sales: 780 },
  { id: 29, title: "全球通史", author: "斯塔夫里阿诺斯", price: 75.0, cover: "https://picsum.photos/300/400?random=29", categoryId: 5, description: "打破地域局限，以全球视角讲述世界历史，兼顾东西方文明发展脉络。", sales: 680 },
  { id: 30, title: "道德经", author: "老子", price: 26.0, cover: "https://picsum.photos/300/400?random=30", categoryId: 5, description: "道家思想经典著作，探讨天道、处世、修身之道，影响中国文化两千余年。", sales: 1500 },

  // ========== 家庭幸福类（categoryId: 6）==========
  { id: 31, title: "亲密关系", author: "罗兰·米勒", price: 42.5, cover: "https://picsum.photos/300/400?random=31", categoryId: 6, description: "亲密关系领域权威著作，深度剖析爱情、婚姻、亲子、人际相处的底层逻辑。", sales: 1200 },
  { id: 32, title: "非暴力沟通", author: "马歇尔·卢森堡", price: 32.0, cover: "https://picsum.photos/300/400?random=32", categoryId: 6, description: "风靡全球的沟通指南，提出观察、感受、需要、请求四步沟通法。", sales: 1400 },
  { id: 33, title: "人间值得", author: "中村恒子", price: 29.0, cover: "https://picsum.photos/300/400?random=33", categoryId: 6, description: "90岁心理医生的人生感悟，文字温柔治愈，告诉读者平淡生活也值得珍惜。", sales: 1100 },
  { id: 34, title: "原生家庭", author: "苏珊·福沃德", price: 33.8, cover: "https://picsum.photos/300/400?random=34", categoryId: 6, description: "解析原生家庭对性格、婚恋、亲子关系的影响，帮助读者与家人和解。", sales: 950 },
  { id: 35, title: "幸福的婚姻", author: "约翰·戈特曼", price: 38.9, cover: "https://picsum.photos/300/400?random=35", categoryId: 6, description: "婚姻心理学经典，总结维系婚姻的七大法则，教夫妻长久经营幸福婚姻。", sales: 880 },
  { id: 36, title: "正面管教", author: "简·尼尔森", price: 36.0, cover: "https://picsum.photos/300/400?random=36", categoryId: 6, description: "经典亲子教育书籍，倡导温和而坚定的教育方式，培养自律自信的孩子。", sales: 1050 },

  // ========== 数学类（categoryId: 7）==========
  { id: 37, title: "高等数学（上册）", author: "同济大学数学系", price: 49.0, cover: "https://picsum.photos/300/400?random=37", categoryId: 7, description: "高等数学经典教材，涵盖函数与极限、导数与微分、不定积分、定积分等内容。", sales: 580 },
  { id: 38, title: "高等数学（下册）", author: "同济大学数学系", price: 52.0, cover: "https://picsum.photos/300/400?random=38", categoryId: 7, description: "承接上册内容，包含空间解析几何、多元函数微分、重积分、无穷级数等核心章节。", sales: 520 },
  { id: 39, title: "线性代数", author: "同济大学数学系", price: 39.0, cover: "https://picsum.photos/300/400?random=39", categoryId: 7, description: "系统讲解矩阵理论、向量空间、线性变换、特征值与特征向量、二次型等核心内容。", sales: 460 },
  { id: 40, title: "概率论与数理统计", author: "盛骤", price: 45.0, cover: "https://picsum.photos/300/400?random=40", categoryId: 7, description: "概率论基础、随机变量及其分布、参数估计、假设检验等，理论与应用兼顾。", sales: 420 },

  // ========== 英语类（categoryId: 8）==========
  { id: 41, title: "大学英语（第一册）", author: "李荫华", price: 32.0, cover: "https://picsum.photos/300/400?random=41", categoryId: 8, description: "大学英语综合教程第一册，精选贴近大学生生活的主题文章，全面提升英语综合能力。", sales: 890 },
  { id: 42, title: "大学英语（第二册）", author: "李荫华", price: 32.0, cover: "https://picsum.photos/300/400?random=42", categoryId: 8, description: "延续第一册风格，主题更加丰富，涉及科技发展、环境保护、社会热点等。", sales: 780 },

  // ========== 计算机类（categoryId: 9）==========
  { id: 43, title: "C程序设计", author: "谭浩强", price: 39.0, cover: "https://picsum.photos/300/400?random=43", categoryId: 9, description: "C语言入门经典教材，从基本语法到指针、结构体、文件操作等核心内容。", sales: 680 },
  { id: 44, title: "数据结构", author: "严蔚敏", price: 48.0, cover: "https://picsum.photos/300/400?random=44", categoryId: 9, description: "数据结构与算法经典教材，系统讲解线性表、栈与队列、树与二叉树、图、查找、排序等。", sales: 550 },
  { id: 45, title: "计算机组成原理", author: "唐朔飞", price: 55.0, cover: "https://picsum.photos/300/400?random=45", categoryId: 9, description: "深入讲解计算机硬件系统原理，涵盖数据表示、指令系统、CPU、存储系统、IO系统。", sales: 420 },
  { id: 46, title: "操作系统", author: "汤小丹", price: 49.0, cover: "https://picsum.photos/300/400?random=46", categoryId: 9, description: "操作系统原理与实现教材，讲解进程管理、内存管理、文件管理、设备管理等核心机制。", sales: 480 },
  { id: 47, title: "计算机网络", author: "谢希仁", price: 52.0, cover: "https://picsum.photos/300/400?random=47", categoryId: 9, description: "计算机网络原理与协议教材，从OSI七层模型入手，讲解各层协议与技术。", sales: 510 },
  { id: 48, title: "数据库系统概论", author: "王珊", price: 46.0, cover: "https://picsum.photos/300/400?random=48", categoryId: 9, description: "数据库原理与应用教材，讲解关系数据库理论、SQL语言、数据库设计、事务管理等。", sales: 440 },
  { id: 49, title: "Java程序设计", author: "陈国君", price: 45.0, cover: "https://picsum.photos/300/400?random=49", categoryId: 9, description: "Java语言编程基础教材，从Java语法、面向对象特性入手，讲解核心内容。", sales: 560 },
  { id: 50, title: "软件工程", author: "张海藩", price: 42.0, cover: "https://picsum.photos/300/400?random=50", categoryId: 9, description: "软件工程原理与实践教材，讲解软件生命周期、需求分析、系统设计、测试维护等。", sales: 380 },
  { id: 51, title: "Python编程从入门到实践", author: "Eric Matthes", price: 79.0, cover: "https://picsum.photos/300/400?random=51", categoryId: 9, description: "Python编程入门经典畅销书，项目驱动学习方式，包含三个实战项目。", sales: 720 },
  { id: 52, title: "Vue.js设计与实现", author: "霍春阳", price: 89.0, cover: "https://picsum.photos/300/400?random=52", categoryId: 9, description: "深入理解Vue.js内部机制的进阶读物，剖析响应式系统、虚拟DOM等核心模块。", sales: 450 },
  { id: 53, title: "算法导论", author: "Thomas H.Cormen", price: 128.0, cover: "https://picsum.photos/300/400?random=53", categoryId: 9, description: "算法设计与分析经典巨著，涵盖排序、搜索、图算法、动态规划等核心内容。", sales: 390 },

  // ========== 思想政治类（categoryId: 10）==========
  { id: 54, title: "马克思主义基本原理", author: "本书编写组", price: 39.0, cover: "https://picsum.photos/300/400?random=54", categoryId: 10, description: "马克思主义基本原理概论教材，系统讲解马克思主义哲学、政治经济学、科学社会主义。", sales: 1800 },
  { id: 55, title: "毛泽东思想概论", author: "本书编写组", price: 36.0, cover: "https://picsum.photos/300/400?random=55", categoryId: 10, description: "毛泽东思想基本内容教材，讲解毛泽东思想的形成发展与核心理论。", sales: 1500 },
  { id: 56, title: "思想道德修养", author: "本书编写组", price: 32.0, cover: "https://picsum.photos/300/400?random=56", categoryId: 10, description: "思想道德修养与法律基础教材，涵盖理想信念、爱国主义、道德规范、法律基础等。", sales: 1600 },
  { id: 57, title: "中国近代史纲要", author: "本书编写组", price: 34.0, cover: "https://picsum.photos/300/400?random=57", categoryId: 10, description: "中国近现代史纲要教材，梳理从1840年鸦片战争至今的历史发展脉络。", sales: 1400 },

  // ========== 自然科学类（categoryId: 11）==========
  { id: 58, title: "物理学（上册）", author: "马文蔚", price: 54.0, cover: "https://picsum.photos/300/400?random=58", categoryId: 11, description: "大学物理上册，涵盖力学与热学两大板块，理论严谨、例题丰富。", sales: 620 },
  { id: 59, title: "物理学（下册）", author: "马文蔚", price: 56.0, cover: "https://picsum.photos/300/400?random=59", categoryId: 11, description: "大学物理下册，涵盖电磁学与光学两大板块，是理工科专业物理学进阶教材。", sales: 580 },
  { id: 60, title: "化学原理", author: "胡忠鲠", price: 48.0, cover: "https://picsum.photos/300/400?random=60", categoryId: 11, description: "普通化学原理教材，涵盖物质结构、化学反应原理、溶液与胶体、电化学基础等。", sales: 450 },

  // ========== 经管类（categoryId: 12）==========
  { id: 61, title: "管理学原理", author: "周三多", price: 45.0, cover: "https://picsum.photos/300/400?random=61", categoryId: 12, description: "管理学基本原理与方法教材，系统讲解管理思想发展、计划、组织、领导、控制职能等。", sales: 520 },
  { id: 62, title: "经济学原理", author: "曼昆", price: 68.0, cover: "https://picsum.photos/300/400?random=62", categoryId: 12, description: "经济学入门经典教材，分为微观经济学与宏观经济学两部分，语言通俗、案例丰富。", sales: 680 },
  { id: 63, title: "市场营销学", author: "菲利普·科特勒", price: 59.0, cover: "https://picsum.photos/300/400?random=63", categoryId: 12, description: "市场营销基本理论教材，涵盖市场调研、消费者行为分析、产品定价渠道促销策略等。", sales: 480 },
  { id: 64, title: "会计学基础", author: "葛家澍", price: 42.0, cover: "https://picsum.photos/300/400?random=64", categoryId: 12, description: "会计学基础知识教材，讲解会计基本概念、会计要素、账户与复式记账、财务报表编制等。", sales: 550 }
];

// ==================== 数据查询函数 ====================

/**
 * 根据分类ID筛选书籍
 * 
 * @param categoryId - 分类ID（1-12）
 * @returns 返回该分类下的所有书籍数组
 * 
 * 使用示例：
 * getBooksByCategory(1)  // 返回经典文学类的6本书
 */
export const getBooksByCategory = (categoryId) => {
  return BOOK_LIST.filter(book => book.categoryId === categoryId);
};

/**
 * 根据书籍ID获取单本书籍
 * 
 * @param id - 书籍ID
 * @returns 返回匹配的书籍对象，找不到则返回 undefined
 * 
 * 注意：参数会被转换为数字类型，所以传入字符串ID也能正常工作
 */
export const getBookById = (id) => {
  return BOOK_LIST.find(book => book.id === Number(id));
};

/**
 * 根据关键词搜索书籍
 * 
 * @param keyword - 搜索关键词
 * @returns 返回书名或作者包含关键词的书籍数组
 * 
 * 搜索范围：书名和作者名（不区分大小写）
 */
export const searchBooks = (keyword) => {
  const lowerKeyword = keyword.toLowerCase();
  return BOOK_LIST.filter(book =>
    book.title.toLowerCase().includes(lowerKeyword) ||
    book.author.toLowerCase().includes(lowerKeyword)
  );
};

/**
 * 获取销量最高的书籍
 * 
 * @param limit - 返回数量，默认6本
 * @returns 返回销量排名前N的书籍数组
 * 
 * 按销量降序排列，用于首页热销推荐展示
 */
export const getTopSellingBooks = (limit = 6) => {
  return [...BOOK_LIST].sort((a, b) => b.sales - a.sales).slice(0, limit);
};