/**
 * 书籍详情数据文件
 * 
 * 本文件是书籍商城的核心数据源，包含：
 * 1. 分类列表 - 用于分类导航
 * 2. 书籍详情 - 包含完整的书籍信息（比 books.js 更详细）
 * 3. 工具函数 - 用于查询和操作书籍数据
 * 4. 浏览量追踪 - 记录用户浏览和点击行为
 * 5. 书评数据 - 每本书的用户评论
 * 
 * 分类体系共12个分类，ID对应关系：
 * 1-经典文学  2-名人传记  3-心理学著作  4-个人成长  5-历史文化  6-家庭幸福
 * 7-数学      8-英语      9-计算机      10-思想政治 11-自然科学 12-经管
 */

// ==================== 分类数据 ====================

/**
 * 书籍分类列表
 * 
 * 数据结构：
 * - id: 分类唯一标识
 * - name: 分类显示名称
 */
export const categoryList = [
  // 全品类书籍（适合普通读者）
  { id: 1, name: '经典文学类' },
  { id: 2, name: '名人传记类' },
  { id: 3, name: '心理学著作类' },
  { id: 4, name: '个人成长类' },
  { id: 5, name: '历史文化类' },
  { id: 6, name: '家庭幸福类' },
  // 教材类书籍（适合学生群体）
  { id: 7, name: '数学' },
  { id: 8, name: '英语' },
  { id: 9, name: '计算机' },
  { id: 10, name: '思想政治' },
  { id: 11, name: '自然科学' },
  { id: 12, name: '经管' }
]

// ==================== 书籍详情数据 ====================

/**
 * 全部书籍详情列表（共64本）
 * 
 * 数据结构说明：
 * - id: 书籍唯一标识
 * - title: 书名
 * - author: 作者
 * - price: 价格（单位：元）
 * - cover: 封面图片地址（使用 picsum.photos 随机图片服务）
 * - categoryId: 所属分类ID（对应 categoryList）
 * - description: 书籍详细介绍（比 books.js 更详细）
 * - sales: 销量数据
 */
export const bookList = [
  // ===================== 1. 经典文学类（categoryId: 1，共6本）=====================
  {
    id: 1,
    title: '边城',
    author: '沈从文',
    price: 32.80,
    cover: 'https://picsum.photos/300/400?random=1',
    categoryId: 1,
    description: '《边城》是沈从文的代表作，以湘西茶峒为背景，讲述翠翠、天保、傩送之间纯粹又凄美的爱情故事，勾勒出田园牧歌式的乡土风情，文字纯净优美，尽显人性本真与自然之美。',
    sales: 26800
  },
  {
    id: 2,
    title: '活着',
    author: '余华',
    price: 25.00,
    cover: 'https://picsum.photos/300/400?random=2',
    categoryId: 1,
    description: '讲述主人公福贵一生的坎坷遭遇，历经家道中落、亲人相继离世，却依然坚强地活着。作品用平淡的文字诠释生命的韧性，是中国当代文学的经典之作。',
    sales: 58600
  },
  {
    id: 3,
    title: '追风筝的人',
    author: '卡勒德·胡赛尼',
    price: 38.50,
    cover: 'https://picsum.photos/300/400?random=3',
    categoryId: 1,
    description: '一部关于救赎与成长的文学佳作，故事发生在阿富汗，主角在愧疚中踏上自我救赎之路，情节动人，探讨人性、背叛与善良，风靡全球数十年。',
    sales: 42300
  },
  {
    id: 4,
    title: '百年孤独',
    author: '加西亚·马尔克斯',
    price: 55.00,
    cover: 'https://picsum.photos/300/400?random=4',
    categoryId: 1,
    description: '魔幻现实主义文学的巅峰之作，以布恩迪亚家族七代人的兴衰为主线，描绘马孔多小镇的百年变迁，充满奇幻色彩与深刻的时代隐喻。',
    sales: 35100
  },
  {
    id: 5,
    title: '红楼梦',
    author: '曹雪芹',
    price: 68.00,
    cover: 'https://picsum.photos/300/400?random=5',
    categoryId: 1,
    description: '中国古典四大名著之首，以贾、史、王、薛四大家族的兴衰为背景，围绕贾宝玉、林黛玉、薛宝钗的爱情悲剧展开，囊括诗词、礼仪、民俗，是传统文化的集大成之作。',
    sales: 61200
  },
  {
    id: 6,
    title: '平凡的世界',
    author: '路遥',
    price: 72.00,
    cover: 'https://picsum.photos/300/400?random=6',
    categoryId: 1,
    description: '全景式展现上世纪七八十年代中国城乡社会生活，讲述孙少安、孙少平兄弟在苦难中奋斗、追寻理想的故事，激励无数普通人直面生活、勇敢前行。',
    sales: 49500
  },

  // ===================== 2. 名人传记类（categoryId: 2，共6本）=====================
  {
    id: 7,
    title: '苏东坡传',
    author: '林语堂',
    price: 36.90,
    cover: 'https://picsum.photos/300/400?random=7',
    categoryId: 2,
    description: '林语堂笔下的苏东坡，还原了这位宋代大文豪跌宕起伏的一生。仕途坎坷却豁达乐观，诗词书画无一不精，兼具才情与风骨，读懂苏轼，便是读懂人生豁达之道。',
    sales: 32600
  },
  {
    id: 8,
    title: '杨绛传',
    author: '罗银胜',
    price: 34.00,
    cover: 'https://picsum.photos/300/400?random=8',
    categoryId: 2,
    description: '记录杨绛先生百年人生，她温婉坚韧、学识渊博，与钱钟书相守一生，在时代风雨中坚守本心，文字平和从容，尽显知识分子的风骨与智慧。',
    sales: 28900
  },
  {
    id: 9,
    title: '人类群星闪耀时',
    author: '斯蒂芬·茨威格',
    price: 29.80,
    cover: 'https://picsum.photos/300/400?random=9',
    categoryId: 2,
    description: '选取历史上14个决定性瞬间，描写哥伦布、拿破仑、托尔斯泰等伟人的抉择与命运，一个个高光时刻串联起人类文明的发展脉络，文笔极具感染力。',
    sales: 37200
  },
  {
    id: 10,
    title: '成为波伏瓦',
    author: '凯特·柯克帕特里克',
    price: 45.00,
    cover: 'https://picsum.photos/300/400?random=10',
    categoryId: 2,
    description: '深度解读女权先驱、哲学家波伏瓦的一生，剖析她的思想、情感与人生选择，展现一位独立女性挣脱时代束缚、追寻自我的传奇历程。',
    sales: 21500
  },
  {
    id: 11,
    title: '苏轼十讲',
    author: '朱刚',
    price: 42.00,
    cover: 'https://picsum.photos/300/400?random=11',
    categoryId: 2,
    description: '复旦大学教授力作，从十个维度解读苏轼的人生、诗词与思想，结合历史背景解析乌台诗案、黄州贬谪等关键经历，是了解苏轼的优质入门读物。',
    sales: 19800
  },
  {
    id: 12,
    title: '拿破仑传',
    author: '艾米尔·路德维希',
    price: 39.50,
    cover: 'https://picsum.photos/300/400?random=12',
    categoryId: 2,
    description: '客观还原拿破仑从平民到法兰西皇帝的传奇一生，讲述他征战欧洲、推行改革的辉煌，也记录其最终陨落的结局，剖析一代枭雄的功与过。',
    sales: 24300
  },

  // ===================== 3. 心理学著作类（categoryId: 3，共6本）=====================
  {
    id: 13,
    title: '蛤蟆先生去看心理医生',
    author: '罗伯特·戴博德',
    price: 33.00,
    cover: 'https://picsum.photos/300/400?random=13',
    categoryId: 3,
    description: '以童话形式讲述心理咨询全过程，蛤蟆先生在咨询师引导下探索情绪、原生家庭与自我认知，通俗易懂，帮助读者走出内耗、治愈心理困境。',
    sales: 52800
  },
  {
    id: 14,
    title: '被讨厌的勇气',
    author: '岸见一郎、古贺史健',
    price: 31.80,
    cover: 'https://picsum.photos/300/400?random=14',
    categoryId: 3,
    description: '基于阿德勒心理学创作，核心观点：自由就是拥有被别人讨厌的勇气。帮助读者摆脱讨好型人格、放下他人期待，学会为自己而活。',
    sales: 47600
  },
  {
    id: 15,
    title: '自卑与超越',
    author: '阿尔弗雷德·阿德勒',
    price: 28.00,
    cover: 'https://picsum.photos/300/400?random=15',
    categoryId: 3,
    description: '心理学经典名著，解读自卑感的来源与影响，提出自卑并非弱点，而是人类进步的动力，教会读者正视自卑、突破自我局限。',
    sales: 39200
  },
  {
    id: 16,
    title: '心流',
    author: '米哈里·契克森米哈赖',
    price: 48.00,
    cover: 'https://picsum.photos/300/400?random=16',
    categoryId: 3,
    description: '"心流"理论开山之作，讲解如何在工作、学习、生活中进入专注忘我的状态，提升幸福感与做事效率，告别焦虑与浮躁。',
    sales: 27500
  },
  {
    id: 17,
    title: '当下的力量',
    author: '埃克哈特·托利',
    price: 35.00,
    cover: 'https://picsum.photos/300/400?random=17',
    categoryId: 3,
    description: '引导人们放下对过去的悔恨、对未来的焦虑，专注活在当下。帮助读者觉察自我、摆脱思维枷锁，获得内心的平静与力量。',
    sales: 31400
  },
  {
    id: 18,
    title: '津巴多普通心理学',
    author: '菲利普·津巴多',
    price: 89.00,
    cover: 'https://picsum.photos/300/400?random=18',
    categoryId: 3,
    description: '全球经典心理学教材，内容全面、案例丰富，涵盖感知、情绪、人格、社会心理等全领域，是心理学入门与进阶的权威读物。',
    sales: 22100
  },

  // ===================== 4. 个人成长类（categoryId: 4，共6本）=====================
  {
    id: 19,
    title: '原子习惯',
    author: '詹姆斯·克利尔',
    price: 45.80,
    cover: 'https://picsum.photos/300/400?random=19',
    categoryId: 4,
    description: '讲解微小习惯的巨大力量，提供四步习惯养成法则，用科学方法帮助读者戒掉坏习惯、建立正向习惯，实现长期自我迭代与成长。',
    sales: 43700
  },
  {
    id: 20,
    title: '刻意练习',
    author: '安德斯·艾利克森',
    price: 36.50,
    cover: 'https://picsum.photos/300/400?random=20',
    categoryId: 4,
    description: '打破"天赋决定一切"的误区，提出顶尖能力源于系统性刻意练习，拆解练习方法、思维模式，适合学生、职场人提升专业能力。',
    sales: 38400
  },
  {
    id: 21,
    title: '自控力',
    author: '凯利·麦格尼格尔',
    price: 32.90,
    cover: 'https://picsum.photos/300/400?random=21',
    categoryId: 4,
    description: '结合神经科学解析自控力原理，剖析拖延、沉迷、冲动等问题，给出可落地的训练方法，帮助读者提升自律能力、掌控生活节奏。',
    sales: 41200
  },
  {
    id: 22,
    title: '少有人走的路',
    author: 'M·斯科特·派克',
    price: 30.00,
    cover: 'https://picsum.photos/300/400?random=22',
    categoryId: 4,
    description: '一部心智成长经典，指出人生本是一连串面对问题、解决问题的过程，教会读者直面痛苦、学会自律、收获成熟的心智。',
    sales: 36800
  },
  {
    id: 23,
    title: '你当像鸟飞往你的山',
    author: '塔拉·韦斯特弗',
    price: 37.00,
    cover: 'https://picsum.photos/300/400?random=23',
    categoryId: 4,
    description: '真实自传体成长故事，作者出身闭塞家庭，靠自学逆袭成为剑桥学者。故事励志动人，诠释教育、勇气与自我重塑的力量。',
    sales: 45300
  },
  {
    id: 24,
    title: '也许你该找个人聊聊',
    author: '洛莉·戈特利布',
    price: 34.80,
    cover: 'https://picsum.photos/300/400?random=24',
    categoryId: 4,
    description: '心理咨询师的回忆录，既是来访者的治愈故事，也是作者自我疗愈的过程。教会人们正视脆弱，学会与情绪共处，拥抱不完美的自己。',
    sales: 33600
  },

  // ===================== 5. 历史文化类（categoryId: 5，共6本）=====================
  {
    id: 25,
    title: '万历十五年',
    author: '黄仁宇',
    price: 35.50,
    cover: 'https://picsum.photos/300/400?random=25',
    categoryId: 5,
    description: '以"大历史观"切入，从平淡的万历十五年入手，通过万历皇帝、张居正、海瑞等人物，剖析明朝制度弊病与王朝衰落的深层原因。',
    sales: 40100
  },
  {
    id: 26,
    title: '人类简史',
    author: '尤瓦尔·赫拉利',
    price: 58.00,
    cover: 'https://picsum.photos/300/400?random=26',
    categoryId: 5,
    description: '从认知革命、农业革命到科学革命，梳理人类数百万年发展历程，探讨人类如何成为地球主宰，视角宏大、观点新颖，颠覆传统历史认知。',
    sales: 51400
  },
  {
    id: 27,
    title: '明朝那些事儿',
    author: '当年明月',
    price: 88.00,
    cover: 'https://picsum.photos/300/400?random=27',
    categoryId: 5,
    description: '用幽默通俗的语言讲述明朝三百年历史，从开国到灭亡，帝王将相、江湖百态娓娓道来，让严肃历史变得生动有趣，老少皆宜。',
    sales: 63700
  },
  {
    id: 28,
    title: '中国通史',
    author: '吕思勉',
    price: 46.00,
    cover: 'https://picsum.photos/300/400?random=28',
    categoryId: 5,
    description: '中国通史经典读本，条理清晰、史料扎实，从上古文明到近代历史完整梳理，是系统了解中国历史与传统文化的入门佳作。',
    sales: 29600
  },
  {
    id: 29,
    title: '全球通史',
    author: '斯塔夫里阿诺斯',
    price: 75.00,
    cover: 'https://picsum.photos/300/400?random=29',
    categoryId: 5,
    description: '打破地域局限，以全球视角讲述世界历史，兼顾东西方文明发展脉络，格局开阔，帮助读者建立全球化历史思维。',
    sales: 34200
  },
  {
    id: 30,
    title: '道德经',
    author: '老子',
    price: 26.00,
    cover: 'https://picsum.photos/300/400?random=30',
    categoryId: 5,
    description: '道家思想经典著作，短短五千字蕴含无穷智慧，探讨天道、处世、修身之道，影响中国文化两千余年，至今仍能启迪人心。',
    sales: 54900
  },

  // ===================== 6. 家庭幸福类（categoryId: 6，共6本）=====================
  {
    id: 31,
    title: '亲密关系',
    author: '罗兰·米勒',
    price: 42.50,
    cover: 'https://picsum.photos/300/400?random=31',
    categoryId: 6,
    description: '亲密关系领域权威著作，深度剖析爱情、婚姻、亲子、人际相处的底层逻辑，讲解沟通、冲突、信任的经营方法，守护家庭与情感。',
    sales: 48300
  },
  {
    id: 32,
    title: '非暴力沟通',
    author: '马歇尔·卢森堡',
    price: 32.00,
    cover: 'https://picsum.photos/300/400?random=32',
    categoryId: 6,
    description: '风靡全球的沟通指南，提出观察、感受、需要、请求四步沟通法，化解家人之间的争吵、误解，打造温暖和谐的家庭氛围。',
    sales: 56100
  },
  {
    id: 33,
    title: '人间值得',
    author: '中村恒子',
    price: 29.00,
    cover: 'https://picsum.photos/300/400?random=33',
    categoryId: 6,
    description: '90岁心理医生的人生感悟，关于家庭、工作、生活与情感。文字温柔治愈，告诉读者不必过度强求，平淡生活也值得用心珍惜。',
    sales: 44700
  },
  {
    id: 34,
    title: '原生家庭',
    author: '苏珊·福沃德',
    price: 33.80,
    cover: 'https://picsum.photos/300/400?random=34',
    categoryId: 6,
    description: '解析原生家庭对性格、婚恋、亲子关系的影响，帮助读者梳理过往创伤，与家人和解，重建健康的家庭相处模式。',
    sales: 39500
  },
  {
    id: 35,
    title: '幸福的婚姻',
    author: '约翰·戈特曼',
    price: 38.90,
    cover: 'https://picsum.photos/300/400?random=35',
    categoryId: 6,
    description: '婚姻心理学经典，基于大量真实家庭案例，总结维系婚姻的七大法则，教夫妻化解矛盾、增进理解，长久经营幸福婚姻。',
    sales: 37200
  },
  {
    id: 36,
    title: '正面管教',
    author: '简·尼尔森',
    price: 36.00,
    cover: 'https://picsum.photos/300/400?random=36',
    categoryId: 6,
    description: '经典亲子教育书籍，拒绝打骂与溺爱，倡导温和而坚定的教育方式，帮助家长建立良好亲子关系，培养自律、自信的孩子。',
    sales: 50800
  },

  // ===================== 7. 数学（categoryId: 7，共4本）=====================
  {
    id: 37,
    title: '高等数学（上册）',
    author: '同济大学数学系',
    price: 49.00,
    cover: 'https://picsum.photos/300/400?random=37',
    categoryId: 7,
    description: '高等数学是理、工、农、医等各专业学生的一门重要基础课。本册涵盖函数与极限、导数与微分、微分中值定理与导数的应用、不定积分、定积分及其应用等内容，体系完整、讲解清晰，是高校广泛采用的经典教材。',
    sales: 1256
  },
  {
    id: 38,
    title: '高等数学（下册）',
    author: '同济大学数学系',
    price: 52.00,
    cover: 'https://picsum.photos/300/400?random=38',
    categoryId: 7,
    description: '承接上册内容，本册包含空间解析几何与向量代数、多元函数微分法及其应用、重积分、曲线积分与曲面积分、无穷级数等核心章节，深入讲解多元微积分理论，配合大量例题与习题，适合理工科专业深入学习。',
    sales: 1123
  },
  {
    id: 39,
    title: '线性代数',
    author: '同济大学数学系',
    price: 39.00,
    cover: 'https://picsum.photos/300/400?random=39',
    categoryId: 7,
    description: '系统讲解矩阵理论、向量空间、线性变换、特征值与特征向量、二次型等核心内容。线性代数是现代数学的重要分支，广泛应用于计算机科学、物理学、经济学等领域，本书结构清晰、例题丰富，是高校标准教材。',
    sales: 986
  },
  {
    id: 40,
    title: '概率论与数理统计',
    author: '盛骤',
    price: 45.00,
    cover: 'https://picsum.photos/300/400?random=40',
    categoryId: 7,
    description: '概率论基础、随机变量及其分布、多维随机变量、数字特征、大数定律与中心极限定理、数理统计基础、参数估计、假设检验等。本书理论与应用兼顾，适合统计学、金融、保险、数据分析等专业学习。',
    sales: 856
  },

  // ===================== 8. 英语（categoryId: 8，共2本）=====================
  {
    id: 41,
    title: '大学英语（第一册）',
    author: '李荫华',
    price: 32.00,
    cover: 'https://picsum.photos/300/400?random=41',
    categoryId: 8,
    description: '大学英语综合教程第一册，精选贴近大学生生活的主题文章，涵盖校园生活、人际交往、文化差异等内容。每单元配有词汇讲解、语法练习、阅读理解与写作训练，全面提升英语综合能力，是高校公共英语必修教材。',
    sales: 2341
  },
  {
    id: 42,
    title: '大学英语（第二册）',
    author: '李荫华',
    price: 32.00,
    cover: 'https://picsum.photos/300/400?random=42',
    categoryId: 8,
    description: '大学英语综合教程第二册，延续第一册风格，主题更加丰富，涉及科技发展、环境保护、社会热点等。文章难度循序渐进，配套听说读写综合训练，帮助学生巩固英语基础、拓展视野、提升跨文化交际能力。',
    sales: 2103
  },

  // ===================== 9. 计算机（categoryId: 9，共11本）=====================
  {
    id: 43,
    title: 'C程序设计',
    author: '谭浩强',
    price: 39.00,
    cover: 'https://picsum.photos/300/400?random=43',
    categoryId: 9,
    description: 'C语言入门经典教材，从基本语法、数据类型、运算符入手，逐步讲解流程控制、函数、数组、指针、结构体、文件操作等核心内容。语言通俗易懂、例题丰富实用，是国内高校广泛采用的C语言教学用书。',
    sales: 3421
  },
  {
    id: 44,
    title: '数据结构',
    author: '严蔚敏',
    price: 48.00,
    cover: 'https://picsum.photos/300/400?random=44',
    categoryId: 9,
    description: '数据结构与算法经典教材，系统讲解线性表、栈与队列、串、树与二叉树、图、查找、排序等核心数据结构。理论与实践结合，配有C语言实现代码，是计算机专业必修课程的核心教材，为后续算法学习奠定基础。',
    sales: 1876
  },
  {
    id: 45,
    title: '计算机组成原理',
    author: '唐朔飞',
    price: 55.00,
    cover: 'https://picsum.photos/300/400?random=45',
    categoryId: 9,
    description: '深入讲解计算机硬件系统原理，涵盖数据表示、指令系统、中央处理器、存储系统、输入输出系统等核心模块。帮助读者理解计算机底层工作机制，是计算机专业理解软硬件协同的重要基础课程。',
    sales: 1234
  },
  {
    id: 46,
    title: '操作系统',
    author: '汤小丹',
    price: 49.00,
    cover: 'https://picsum.photos/300/400?random=46',
    categoryId: 9,
    description: '操作系统原理与实现教材，讲解进程管理、内存管理、文件管理、设备管理、作业调度等核心机制。结合典型操作系统实例分析，帮助读者理解操作系统如何协调软硬件资源，是计算机专业核心必修课程。',
    sales: 1567
  },
  {
    id: 47,
    title: '计算机网络',
    author: '谢希仁',
    price: 52.00,
    cover: 'https://picsum.photos/300/400?random=47',
    categoryId: 9,
    description: '计算机网络原理与协议教材，从OSI七层模型入手，讲解物理层、数据链路层、网络层、传输层、应用层各层协议与技术。涵盖TCP/IP、HTTP、DNS等核心协议，帮助读者理解网络通信原理，是网络工程师必修课程。',
    sales: 1789
  },
  {
    id: 48,
    title: '数据库系统概论',
    author: '王珊',
    price: 46.00,
    cover: 'https://picsum.photos/300/400?random=48',
    categoryId: 9,
    description: '数据库原理与应用教材，讲解关系数据库理论、SQL语言、数据库设计、事务管理、并发控制、恢复技术等内容。结合典型数据库管理系统案例，是计算机专业理解数据存储与管理的核心课程。',
    sales: 1432
  },
  {
    id: 49,
    title: 'Java程序设计',
    author: '陈国君',
    price: 45.00,
    cover: 'https://picsum.photos/300/400?random=49',
    categoryId: 9,
    description: 'Java语言编程基础教材，从Java语法、面向对象特性入手，讲解类与对象、继承与多态、接口与抽象类、异常处理、集合框架、IO流、多线程等核心内容。案例丰富、讲解清晰，适合零基础入门Java编程。',
    sales: 1923
  },
  {
    id: 50,
    title: '软件工程',
    author: '张海藩',
    price: 42.00,
    cover: 'https://picsum.photos/300/400?random=50',
    categoryId: 9,
    description: '软件工程原理与实践教材，讲解软件生命周期、需求分析、系统设计、编码实现、测试维护、项目管理等内容。结合实际案例，帮助读者理解软件开发规范流程，是计算机专业理解工程化开发的核心课程。',
    sales: 1102
  },
  {
    id: 51,
    title: 'Python编程从入门到实践',
    author: 'Eric Matthes',
    price: 79.00,
    cover: 'https://picsum.photos/300/400?random=51',
    categoryId: 9,
    description: 'Python编程入门经典畅销书，从基础语法入手，逐步讲解数据结构、函数、类、文件处理、Web开发、数据可视化等内容。项目驱动学习方式，包含游戏开发、Web应用、数据可视化三个实战项目，适合零基础自学。',
    sales: 3456
  },
  {
    id: 52,
    title: 'Vue.js设计与实现',
    author: '霍春阳',
    price: 89.00,
    cover: 'https://picsum.photos/300/400?random=52',
    categoryId: 9,
    description: '深入理解Vue.js内部机制的进阶读物，从响应式系统、虚拟DOM、编译器、组件系统等核心模块入手，剖析Vue.js的设计思想与实现原理。适合有一定Vue基础的前端开发者深入理解框架本质。',
    sales: 2134
  },
  {
    id: 53,
    title: '算法导论',
    author: 'Thomas H.Cormen',
    price: 128.00,
    cover: 'https://picsum.photos/300/400?random=53',
    categoryId: 9,
    description: '算法设计与分析经典巨著，涵盖排序、搜索、图算法、动态规划、贪心算法、字符串匹配、NP完全问题等核心内容。理论严谨、内容全面，是计算机专业算法课程的权威教材，也是程序员进阶必读经典。',
    sales: 1876
  },

  // ===================== 10. 思想政治（categoryId: 10，共4本）=====================
  {
    id: 54,
    title: '马克思主义基本原理',
    author: '本书编写组',
    price: 39.00,
    cover: 'https://picsum.photos/300/400?random=54',
    categoryId: 10,
    description: '马克思主义基本原理概论教材，系统讲解马克思主义哲学、政治经济学、科学社会主义三大组成部分。帮助大学生理解马克思主义世界观与方法论，认识社会发展规律，是高校思想政治理论课必修教材。',
    sales: 5678
  },
  {
    id: 55,
    title: '毛泽东思想概论',
    author: '本书编写组',
    price: 36.00,
    cover: 'https://picsum.photos/300/400?random=55',
    categoryId: 10,
    description: '毛泽东思想基本内容教材，讲解毛泽东思想的形成发展、新民主主义革命理论、社会主义革命与建设理论、党的建设理论等核心内容。帮助大学生理解中国革命与建设的历史经验，是高校思政必修课程。',
    sales: 4321
  },
  {
    id: 56,
    title: '思想道德修养',
    author: '本书编写组',
    price: 32.00,
    cover: 'https://picsum.photos/300/400?random=56',
    categoryId: 10,
    description: '思想道德修养与法律基础教材，涵盖理想信念、爱国主义、人生价值、道德规范、法律基础等内容。帮助大学生树立正确的人生观、价值观、道德观与法治观念，是高校思政必修课程。',
    sales: 3890
  },
  {
    id: 57,
    title: '中国近代史纲要',
    author: '本书编写组',
    price: 34.00,
    cover: 'https://picsum.photos/300/400?random=57',
    categoryId: 10,
    description: '中国近现代史纲要教材，从1840年鸦片战争至今，梳理中国近代历史发展脉络，讲解反侵略斗争、辛亥革命、新民主主义革命、社会主义建设等重大历史事件。帮助大学生理解历史发展规律，是高校思政必修课程。',
    sales: 4123
  },

  // ===================== 11. 自然科学（categoryId: 11，共3本）=====================
  {
    id: 58,
    title: '物理学（上册）',
    author: '马文蔚',
    price: 54.00,
    cover: 'https://picsum.photos/300/400?random=58',
    categoryId: 11,
    description: '大学物理上册，涵盖力学与热学两大板块。力学部分讲解运动学、动力学、刚体转动、振动与波；热学部分讲解热力学基础、气体动理论。理论严谨、例题丰富，是理工科专业物理学基础教材。',
    sales: 1567
  },
  {
    id: 59,
    title: '物理学（下册）',
    author: '马文蔚',
    price: 56.00,
    cover: 'https://picsum.photos/300/400?random=59',
    categoryId: 11,
    description: '大学物理下册，涵盖电磁学与光学两大板块。电磁学部分讲解静电场、稳恒电流、磁场、电磁感应；光学部分讲解光的干涉、衍射、偏振。承接上册内容，是理工科专业物理学进阶教材。',
    sales: 1432
  },
  {
    id: 60,
    title: '化学原理',
    author: '胡忠鲠',
    price: 48.00,
    cover: 'https://picsum.photos/300/400?random=60',
    categoryId: 11,
    description: '普通化学原理教材，涵盖物质结构、化学反应原理、溶液与胶体、电化学基础、有机化学基础等内容。理论讲解清晰，配合实验案例，是化学、化工、材料、环境等专业的基础课程教材。',
    sales: 987
  },

  // ===================== 12. 经管（categoryId: 12，共4本）=====================
  {
    id: 61,
    title: '管理学原理',
    author: '周三多',
    price: 45.00,
    cover: 'https://picsum.photos/300/400?random=61',
    categoryId: 12,
    description: '管理学基本原理与方法教材，系统讲解管理思想发展、计划职能、组织职能、领导职能、控制职能等核心内容。结合企业管理案例，帮助读者理解现代管理理论与实践，是经管专业必修基础课程。',
    sales: 1234
  },
  {
    id: 62,
    title: '经济学原理',
    author: '曼昆',
    price: 68.00,
    cover: 'https://picsum.photos/300/400?random=62',
    categoryId: 12,
    description: '经济学入门经典教材，分为微观经济学与宏观经济学两部分。讲解供需理论、市场均衡、消费者行为、厂商理论、国民收入、通货膨胀、经济增长等核心概念。语言通俗、案例丰富，是经济学入门首选读物。',
    sales: 2345
  },
  {
    id: 63,
    title: '市场营销学',
    author: '菲利普·科特勒',
    price: 59.00,
    cover: 'https://picsum.photos/300/400?random=63',
    categoryId: 12,
    description: '市场营销基本理论教材，涵盖市场调研、消费者行为分析、产品策略、定价策略、渠道策略、促销策略、品牌管理等核心内容。结合大量企业营销案例，是市场营销专业必修课程。',
    sales: 1876
  },
  {
    id: 64,
    title: '会计学基础',
    author: '葛家澍',
    price: 42.00,
    cover: 'https://picsum.photos/300/400?random=64',
    categoryId: 12,
    description: '会计学基础知识教材，讲解会计基本概念、会计要素、会计等式、账户与复式记账、会计凭证、会计账簿、财务报表编制等核心内容。理论与实务结合，是会计、财务管理专业入门必修课程。',
    sales: 1567
  }
]

// ==================== 数据查询工具函数 ====================

/**
 * 根据分类ID获取分类名称
 * 
 * @param categoryId - 分类ID
 * @returns 分类名称字符串
 * 
 * 支持自定义分类：当ID超出预设分类范围时，会从localStorage读取用户自定义分类
 */
export function getCategoryName(categoryId) {
  // 处理自定义分类（ID大于预设分类数量）
  if (categoryId > categoryList.length) {
    const customs = getCustomCategories()
    const index = categoryId - categoryList.length - 1
    return customs[index] || '其他'
  }
  // 查找预设分类
  const category = categoryList.find(c => c.id === categoryId)
  return category ? category.name : '未知分类'
}

/**
 * 根据分类ID获取书籍列表
 * 
 * @param categoryId - 分类ID
 * @returns 该分类下的所有书籍数组
 */
export function getBooksByCategory(categoryId) {
  return bookList.filter(book => book.categoryId === categoryId)
}

/**
 * 根据关键词搜索书籍
 * 
 * @param keyword - 搜索关键词
 * @returns 匹配的书籍数组（搜索书名和作者，不区分大小写）
 */
export function searchBooks(keyword) {
  const lowerKeyword = keyword.toLowerCase()
  return bookList.filter(book =>
    book.title.toLowerCase().includes(lowerKeyword) ||
    book.author.toLowerCase().includes(lowerKeyword)
  )
}

/**
 * 根据书籍ID获取书籍详情
 * 
 * @param bookId - 书籍ID（可以是数字或字符串）
 * @returns 书籍对象，找不到返回null
 */
export function getBookById(bookId) {
  const id = Number(bookId)
  return bookList.find(book => book.id === id) || null
}

/**
 * 获取销量最高的书籍
 * 
 * @param limit - 返回数量，默认6本
 * @returns 按销量降序排列的前N本书籍
 */
export function getTopSellingBooks(limit = 6) {
  return [...bookList].sort((a, b) => b.sales - a.sales).slice(0, limit)
}

// 导出所有书籍列表（别名）
export const allBooks = bookList

// 导出分类名称数组（不含ID）
export const categories = categoryList.map(cat => cat.name)

/**
 * 保存书籍信息（更新现有书籍）
 * 
 * @param book - 要保存的书籍对象
 * 
 * 在数组中找到对应ID的书籍并更新其信息
 */
export function saveBook(book) {
  const index = bookList.findIndex(b => b.id === book.id)
  if (index !== -1) {
    bookList[index] = { ...book }
  }
}

// ==================== 自定义分类管理 ====================

// localStorage存储键名
const CUSTOM_CATEGORIES_KEY = 'customCategories'

/**
 * 获取用户自定义的分类列表
 * 
 * @returns 自定义分类名称数组
 * 
 * 数据存储在localStorage中，持久化保存
 */
export function getCustomCategories() {
  const data = localStorage.getItem(CUSTOM_CATEGORIES_KEY)
  return data ? JSON.parse(data) : []
}

/**
 * 添加自定义分类
 * 
 * @param categoryName - 分类名称
 * @returns 更新后的自定义分类数组
 * 
 * 重复的分类名称不会重复添加
 */
export function addCustomCategory(categoryName) {
  const customs = getCustomCategories()
  if (!customs.includes(categoryName)) {
    customs.push(categoryName)
    localStorage.setItem(CUSTOM_CATEGORIES_KEY, JSON.stringify(customs))
  }
  return customs
}

// ==================== 浏览量/点击量追踪模块 ====================
/**
 * 用户行为统计模块
 * 
 * 记录用户对书籍的浏览和点击行为，数据存储在localStorage
 * 用于统计分析热门书籍、用户兴趣等
 */

// localStorage存储键名
const BOOK_STATS_KEY = 'bookStats'

/**
 * 从localStorage获取所有书籍的统计数据
 * 
 * @returns 统计数据对象，格式：{ bookId: { views: 数量, clicks: 数量 } }
 */
function getBookStats() {
  const data = localStorage.getItem(BOOK_STATS_KEY)
  return data ? JSON.parse(data) : {}
}

/**
 * 将书籍统计数据保存到localStorage
 * 
 * @param stats - 统计数据对象
 */
function saveBookStats(stats) {
  localStorage.setItem(BOOK_STATS_KEY, JSON.stringify(stats))
}

/**
 * 获取指定书籍的浏览量
 * 
 * @param bookId - 书籍ID
 * @returns 浏览次数
 */
export function getBookViews(bookId) {
  const stats = getBookStats()
  return stats[bookId] && stats[bookId].views ? stats[bookId].views : 0
}

/**
 * 获取指定书籍的点击量
 * 
 * @param bookId - 书籍ID
 * @returns 点击次数
 */
export function getBookClicks(bookId) {
  const stats = getBookStats()
  return stats[bookId] && stats[bookId].clicks ? stats[bookId].clicks : 0
}

/**
 * 增加指定书籍的浏览量（+1）
 * 
 * @param bookId - 书籍ID
 * @returns 更新后的浏览量
 */
export function incrementBookViews(bookId) {
  const stats = getBookStats()
  
  // 如果该书还没有统计数据，初始化为0
  if (!stats[bookId]) {
    stats[bookId] = { views: 0, clicks: 0 }
  }
  
  stats[bookId].views += 1
  saveBookStats(stats)
  
  return stats[bookId].views
}

/**
 * 增加指定书籍的点击量（+1）
 * 
 * @param bookId - 书籍ID
 * @returns 更新后的点击量
 */
export function incrementBookClicks(bookId) {
  const stats = getBookStats()
  
  if (!stats[bookId]) {
    stats[bookId] = { views: 0, clicks: 0 }
  }
  
  stats[bookId].clicks += 1
  saveBookStats(stats)
  
  return stats[bookId].clicks
}

/**
 * 获取所有书籍的统计数据
 * 
 * @returns 完整的统计数据对象
 */
export function getAllBookStats() {
  return getBookStats()
}

/**
 * 计算所有书籍的总浏览量
 * 
 * @returns 总浏览次数
 */
export function getTotalViews() {
  const stats = getBookStats()
  return Object.values(stats).reduce((sum, s) => sum + (s.views || 0), 0)
}

/**
 * 计算所有书籍的总点击量
 * 
 * @returns 总点击次数
 */
export function getTotalClicks() {
  const stats = getBookStats()
  return Object.values(stats).reduce((sum, s) => sum + (s.clicks || 0), 0)
}

/**
 * 获取所有分类（预设分类 + 用户自定义分类）
 * 
 * @returns 分类名称数组
 */
export function getAllCategories() {
  return [...categories, ...getCustomCategories()]
}

/**
 * 根据分类名称获取分类ID
 * 
 * @param categoryName - 分类名称
 * @returns 分类ID
 * 
 * 预设分类返回1-12，自定义分类返回13及之后的ID
 */
export function getCategoryIdByName(categoryName) {
  // 先在预设分类中查找
  const catIndex = categories.indexOf(categoryName)
  if (catIndex !== -1) {
    return catIndex + 1
  }
  // 再在自定义分类中查找
  const customs = getCustomCategories()
  const customIndex = customs.indexOf(categoryName)
  if (customIndex !== -1) {
    return categoryList.length + customIndex + 1
  }
  // 找不到返回默认分类ID
  return 1
}

// ==================== 书评数据 ====================
/**
 * 书评数据模块
 * 
 * 每本书配有3-6条模拟评论，用于展示评论功能
 * 用户评论存储在localStorage中，与模拟评论合并显示
 * 
 * 数据结构：
 * - id: 评论唯一标识
 * - bookId: 所属书籍ID
 * - userId: 用户ID
 * - userName: 用户昵称
 * - content: 评论内容
 * - createdAt: 创建时间（ISO格式）
 */
export const mockComments = [
  // ===== 书籍1-6：经典文学类 =====
  { id: 'c1', bookId: 1, userId: 'u1', userName: '书虫小李', content: '沈从文的文字太美了，读完仿佛置身湘西茶峒，翠翠的故事让人久久难忘。', createdAt: '2025-03-15T10:30:00Z' },
  { id: 'c2', bookId: 1, userId: 'u2', userName: '文艺青年', content: '边城是我心中的净土，那种纯朴的人情味在现代社会已经很难找到了。', createdAt: '2025-02-28T14:20:00Z' },
  { id: 'c3', bookId: 1, userId: 'u3', userName: '阅读达人', content: '经典之作，值得反复阅读，每次都能发现新的感动。', createdAt: '2025-01-10T09:15:00Z' },
  { id: 'c4', bookId: 2, userId: 'u4', userName: '人生思考者', content: '读完《活着》，对生命有了新的理解。福贵的坚韧让我反思自己的生活态度。', createdAt: '2025-04-01T16:45:00Z' },
  { id: 'c5', bookId: 2, userId: 'u5', userName: '文学爱好者', content: '余华用最平淡的文字写出最震撼人心的故事，这就是大师的力量。', createdAt: '2025-03-20T11:30:00Z' },
  { id: 'c6', bookId: 2, userId: 'u6', userName: '深夜读者', content: '哭了好几次，但哭过之后反而觉得生活更有意义了。', createdAt: '2025-02-15T22:00:00Z' },
  { id: 'c7', bookId: 3, userId: 'u7', userName: '阿富汗迷', content: '追风筝的人让我了解了阿富汗的历史与文化，哈桑的忠诚令人动容。', createdAt: '2025-05-10T08:00:00Z' },
  { id: 'c8', bookId: 3, userId: 'u8', userName: '救赎之路', content: '"为你，千千万万遍"这句话深深印在我的脑海里。', createdAt: '2025-04-25T13:15:00Z' },
  { id: 'c9', bookId: 3, userId: 'u9', userName: '环球读者', content: '跨越文化与国界的故事，人性的光辉与阴暗都写得淋漓尽致。', createdAt: '2025-03-08T17:30:00Z' },
  { id: 'c10', bookId: 4, userId: 'u10', userName: '魔幻迷', content: '马尔克斯的魔幻现实主义太震撼了，布恩迪亚家族的命运让人唏嘘。', createdAt: '2025-06-01T12:00:00Z' },
  { id: 'c11', bookId: 4, userId: 'u11', userName: '文学研究生', content: '读了好几遍才读懂，但每次都有新的发现，不愧是诺贝尔文学奖作品。', createdAt: '2025-05-15T15:45:00Z' },
  { id: 'c12', bookId: 4, userId: 'u12', userName: '拉美文学', content: '孤独是贯穿全书的主题，每个人都在孤独中挣扎。', createdAt: '2025-04-20T10:30:00Z' },
  { id: 'c13', bookId: 5, userId: 'u13', userName: '国学爱好者', content: '红楼梦是中国文学的巅峰，诗词、礼仪、民俗都写得精妙绝伦。', createdAt: '2025-07-01T09:00:00Z' },
  { id: 'c14', bookId: 5, userId: 'u14', userName: '黛玉粉丝', content: '林黛玉的悲剧命运让我心痛，曹雪芹对女性命运的描写太深刻了。', createdAt: '2025-06-10T14:20:00Z' },
  { id: 'c15', bookId: 5, userId: 'u15', userName: '古典文学', content: '四大名著之首名副其实，每次重读都能发现新的细节。', createdAt: '2025-05-05T11:00:00Z' },
  { id: 'c16', bookId: 6, userId: 'u16', userName: '奋斗青年', content: '孙少平的故事激励了我，平凡人也可以有不平凡的人生。', createdAt: '2025-08-15T16:30:00Z' },
  { id: 'c17', bookId: 6, userId: 'u17', userName: '农村娃', content: '路遥写出了我们农村人的心声，苦难中依然有希望。', createdAt: '2025-07-20T10:15:00Z' },
  { id: 'c18', bookId: 6, userId: 'u18', userName: '励志读者', content: '这本书陪我度过了人生最艰难的时期，给了我继续前行的勇气。', createdAt: '2025-06-25T13:45:00Z' },

  // ===== 书籍7-12：名人传记类 =====
  { id: 'c19', bookId: 7, userId: 'u19', userName: '苏轼迷', content: '林语堂把苏东坡写得活灵活现，读完更爱这位豁达的大文豪了。', createdAt: '2025-03-10T09:30:00Z' },
  { id: 'c20', bookId: 7, userId: 'u20', userName: '文人风骨', content: '苏轼的人生哲学值得学习，逆境中依然保持乐观与幽默。', createdAt: '2025-02-05T14:00:00Z' },
  { id: 'c21', bookId: 8, userId: 'u21', userName: '杨绛粉丝', content: '杨绛先生的从容与智慧让我敬佩，她与钱钟书的爱情令人羡慕。', createdAt: '2025-04-15T11:20:00Z' },
  { id: 'c22', bookId: 8, userId: 'u22', userName: '知识分子', content: '百年人生的记录，展现了一代知识分子的风骨与坚守。', createdAt: '2025-03-25T16:45:00Z' },
  { id: 'c23', bookId: 9, userId: 'u23', userName: '历史迷', content: '茨威格笔下的人类群星闪耀时刻，让人感受到历史的厚重。', createdAt: '2025-05-20T08:15:00Z' },
  { id: 'c24', bookId: 9, userId: 'u24', userName: '伟人崇拜', content: '那些改变历史的瞬间，让我对伟人有了更深的理解。', createdAt: '2025-04-10T13:30:00Z' },
  { id: 'c25', bookId: 10, userId: 'u25', userName: '女性力量', content: '波伏瓦的一生证明了女性可以独立、自由地追寻自我。', createdAt: '2025-06-15T10:00:00Z' },
  { id: 'c26', bookId: 10, userId: 'u26', userName: '哲学爱好者', content: '《第二性》作者的传记，让我更理解女权思想的起源。', createdAt: '2025-05-25T15:20:00Z' },
  { id: 'c27', bookId: 11, userId: 'u27', userName: '复旦学子', content: '朱刚教授的解读非常专业，让我对苏轼有了更全面的认识。', createdAt: '2025-07-10T12:30:00Z' },
  { id: 'c28', bookId: 11, userId: 'u28', userName: '诗词爱好者', content: '十讲的角度很新颖，乌台诗案那段写得特别精彩。', createdAt: '2025-06-20T09:45:00Z' },
  { id: 'c29', bookId: 12, userId: 'u29', userName: '军事迷', content: '拿破仑的传奇一生，从平民到皇帝的逆袭令人震撼。', createdAt: '2025-08-20T14:15:00Z' },
  { id: 'c30', bookId: 12, userId: 'u30', userName: '欧洲历史', content: '路德维希的传记客观公正，既写了辉煌也写了陨落。', createdAt: '2025-07-25T11:00:00Z' },

  // ===== 书籍13-18：心理学著作类 =====
  { id: 'c31', bookId: 13, userId: 'u31', userName: '心理咨询师', content: '用童话形式讲心理咨询，通俗易懂，适合大众阅读。', createdAt: '2025-03-05T10:30:00Z' },
  { id: 'c32', bookId: 13, userId: 'u32', userName: '自我探索', content: '跟着蛤蟆先生一起探索内心，对原生家庭有了新的理解。', createdAt: '2025-02-10T15:45:00Z' },
  { id: 'c33', bookId: 13, userId: 'u33', userName: '治愈读者', content: '读完这本书，感觉自己的内耗减轻了很多。', createdAt: '2025-01-15T09:00:00Z' },
  { id: 'c34', bookId: 14, userId: 'u34', userName: '讨好型人格', content: '这本书改变了我的人生，学会不再为别人而活。', createdAt: '2025-04-20T13:20:00Z' },
  { id: 'c35', bookId: 14, userId: 'u35', userName: '阿德勒粉丝', content: '被讨厌的勇气让我明白，自由就是敢于做自己。', createdAt: '2025-03-30T16:00:00Z' },
  { id: 'c36', bookId: 14, userId: 'u36', userName: '自我成长', content: '对话形式很好读，阿德勒心理学真的很实用。', createdAt: '2025-02-25T11:30:00Z' },
  { id: 'c37', bookId: 15, userId: 'u37', userName: '自卑者', content: '自卑不是弱点，而是进步的动力，这个观点让我释然了。', createdAt: '2025-05-30T08:45:00Z' },
  { id: 'c38', bookId: 15, userId: 'u38', userName: '心理学学生', content: '阿德勒的经典之作，对自卑感的分析非常深刻。', createdAt: '2025-04-15T14:00:00Z' },
  { id: 'c39', bookId: 16, userId: 'u39', userName: '效率达人', content: '心流理论让我学会如何进入专注状态，工作效率提升很多。', createdAt: '2025-06-25T10:15:00Z' },
  { id: 'c40', bookId: 16, userId: 'u40', userName: '幸福感', content: '找到心流状态，生活变得更加充实和快乐。', createdAt: '2025-05-10T13:30:00Z' },
  { id: 'c41', bookId: 17, userId: 'u41', userName: '焦虑者', content: '活在当下，放下对过去的悔恨和对未来的焦虑，内心平静了很多。', createdAt: '2025-07-15T09:20:00Z' },
  { id: 'c42', bookId: 17, userId: 'u42', userName: '冥想爱好者', content: '托利的教导与冥想结合，帮助我摆脱思维的束缚。', createdAt: '2025-06-05T15:45:00Z' },
  { id: 'c43', bookId: 18, userId: 'u43', userName: '心理学入门', content: '津巴多的教材内容全面，是心理学学习的必备读物。', createdAt: '2025-08-10T12:00:00Z' },
  { id: 'c44', bookId: 18, userId: 'u44', userName: '心理学专业', content: '案例丰富，讲解清晰，适合系统学习心理学。', createdAt: '2025-07-20T16:30:00Z' },

  // ===== 书籍19-24：个人成长类 =====
  { id: 'c45', bookId: 19, userId: 'u45', userName: '习惯养成者', content: '原子习惯的四步法则很实用，帮我戒掉了拖延症。', createdAt: '2025-03-20T10:00:00Z' },
  { id: 'c46', bookId: 19, userId: 'u46', userName: '自我迭代', content: '微小习惯的力量太大了，每天进步一点点，一年后变化惊人。', createdAt: '2025-02-15T14:30:00Z' },
  { id: 'c47', bookId: 19, userId: 'u47', userName: '行动派', content: '这本书让我从理论走向实践，真正开始改变自己。', createdAt: '2025-01-20T09:15:00Z' },
  { id: 'c48', bookId: 20, userId: 'u48', userName: '技能提升', content: '刻意练习让我明白，天赋不是决定因素，方法才是关键。', createdAt: '2025-04-25T11:45:00Z' },
  { id: 'c49', bookId: 20, userId: 'u49', userName: '职场人', content: '用刻意练习的方法提升专业技能，效果显著。', createdAt: '2025-03-15T16:20:00Z' },
  { id: 'c50', bookId: 21, userId: 'u50', userName: '拖延症患者', content: '自控力的训练方法很科学，帮我克服了拖延和冲动。', createdAt: '2025-05-15T08:30:00Z' },
  { id: 'c51', bookId: 21, userId: 'u51', userName: '自律达人', content: '麦格尼格尔的书让我理解了自控力的神经科学原理。', createdAt: '2025-04-05T13:00:00Z' },
  { id: 'c52', bookId: 22, userId: 'u52', userName: '心智成长', content: '少有人走的路教会我直面痛苦，心智更加成熟。', createdAt: '2025-06-10T10:45:00Z' },
  { id: 'c53', bookId: 22, userId: 'u53', userName: '人生哲学', content: '派克的观点很深刻，人生本就是解决问题的过程。', createdAt: '2025-05-20T15:15:00Z' },
  { id: 'c54', bookId: 23, userId: 'u54', userName: '励志读者', content: '塔拉的故事太震撼了，教育真的可以改变命运。', createdAt: '2025-07-25T09:30:00Z' },
  { id: 'c55', bookId: 23, userId: 'u55', userName: '逆袭故事', content: '从闭塞家庭到剑桥学者，这才是真正的励志。', createdAt: '2025-06-15T14:00:00Z' },
  { id: 'c56', bookId: 24, userId: 'u56', userName: '情绪管理', content: '找个人聊聊真的很重要，学会正视自己的脆弱。', createdAt: '2025-08-05T11:20:00Z' },
  { id: 'c57', bookId: 24, userId: 'u57', userName: '治愈故事', content: '心理咨询师的回忆录，既是来访者的故事也是作者的治愈。', createdAt: '2025-07-10T16:45:00Z' },

  // ===== 书籍25-30：历史文化类 =====
  { id: 'c58', bookId: 25, userId: 'u58', userName: '明史爱好者', content: '黄仁宇的大历史观很独特，从平淡的一年看明朝衰落。', createdAt: '2025-03-10T10:30:00Z' },
  { id: 'c59', bookId: 25, userId: 'u59', userName: '历史研究生', content: '万历十五年的分析角度很新颖，制度弊病剖析深刻。', createdAt: '2025-02-05T15:00:00Z' },
  { id: 'c60', bookId: 26, userId: 'u60', userName: '全球视野', content: '人类简史的视角太宏大，颠覆了我对历史的认知。', createdAt: '2025-04-20T09:15:00Z' },
  { id: 'c61', bookId: 26, userId: 'u61', userName: '认知革命', content: '赫拉利讲的人类发展历程，让我重新思考文明的本质。', createdAt: '2025-03-25T14:45:00Z' },
  { id: 'c62', bookId: 27, userId: 'u62', userName: '明朝粉丝', content: '当年明月把历史写得生动有趣，三百年风云尽在书中。', createdAt: '2025-05-30T11:00:00Z' },
  { id: 'c63', bookId: 27, userId: 'u63', userName: '通俗历史', content: '幽默的语言让严肃历史变得亲切，老少皆宜。', createdAt: '2025-04-15T16:30:00Z' },
  { id: 'c64', bookId: 28, userId: 'u64', userName: '国学入门', content: '吕思勉的通史条理清晰，是了解中国历史的佳作。', createdAt: '2025-06-25T10:20:00Z' },
  { id: 'c65', bookId: 28, userId: 'u65', userName: '传统文化', content: '从上古到近代，完整梳理，史料扎实。', createdAt: '2025-05-10T13:45:00Z' },
  { id: 'c66', bookId: 29, userId: 'u66', userName: '世界历史', content: '斯塔夫里阿诺斯的全球视角，东西方文明兼顾。', createdAt: '2025-07-15T09:00:00Z' },
  { id: 'c67', bookId: 29, userId: 'u67', userName: '全球化思维', content: '打破地域局限，建立全球化历史思维。', createdAt: '2025-06-05T15:30:00Z' },
  { id: 'c68', bookId: 30, userId: 'u68', userName: '道家思想', content: '道德经五千字蕴含无穷智慧，两千年来依然启迪人心。', createdAt: '2025-08-10T12:15:00Z' },
  { id: 'c69', bookId: 30, userId: 'u69', userName: '哲学爱好者', content: '老子的思想深邃，天道、处世、修身之道值得反复品味。', createdAt: '2025-07-20T16:00:00Z' },

  // ===== 书籍31-36：家庭幸福类 =====
  { id: 'c70', bookId: 31, userId: 'u70', userName: '婚姻咨询师', content: '亲密关系的经营方法很实用，帮助很多家庭化解矛盾。', createdAt: '2025-03-15T10:45:00Z' },
  { id: 'c71', bookId: 31, userId: 'u71', userName: '恋爱中的人', content: '理解了爱情与婚姻的底层逻辑，感情更加稳固。', createdAt: '2025-02-20T14:00:00Z' },
  { id: 'c72', bookId: 32, userId: 'u72', userName: '沟通学习者', content: '非暴力沟通的四步法，让家庭氛围变得温暖和谐。', createdAt: '2025-04-10T09:30:00Z' },
  { id: 'c73', bookId: 32, userId: 'u73', userName: '家庭和睦', content: '学会观察、感受、需要、请求，化解了很多误解。', createdAt: '2025-03-05T15:20:00Z' },
  { id: 'c74', bookId: 33, userId: 'u74', userName: '治愈读者', content: '90岁心理医生的人生感悟，温柔治愈，人间值得。', createdAt: '2025-05-25T11:15:00Z' },
  { id: 'c75', bookId: 33, userId: 'u75', userName: '生活哲学', content: '不必过度强求，平淡生活也值得用心珍惜。', createdAt: '2025-04-20T16:45:00Z' },
  { id: 'c76', bookId: 34, userId: 'u76', userName: '原生家庭', content: '这本书帮我梳理了原生家庭的影响，学会与家人和解。', createdAt: '2025-06-15T10:00:00Z' },
  { id: 'c77', bookId: 34, userId: 'u77', userName: '心理治愈', content: '理解了性格与原生家庭的关系，重建健康的相处模式。', createdAt: '2025-05-05T13:30:00Z' },
  { id: 'c78', bookId: 35, userId: 'u78', userName: '婚姻幸福', content: '戈特曼的七大法则很实用，帮助夫妻长久经营婚姻。', createdAt: '2025-07-20T09:45:00Z' },
  { id: 'c79', bookId: 35, userId: 'u79', userName: '夫妻相处', content: '化解矛盾、增进理解的方法，让婚姻更加稳固。', createdAt: '2025-06-10T14:15:00Z' },
  { id: 'c80', bookId: 36, userId: 'u80', userName: '新手父母', content: '正面管教的方法温和有效，孩子更加自律自信。', createdAt: '2025-08-05T11:30:00Z' },
  { id: 'c81', bookId: 36, userId: 'u81', userName: '教育理念', content: '拒绝打骂与溺爱，温和而坚定的教育方式值得学习。', createdAt: '2025-07-15T16:00:00Z' },

  // ===== 书籍37-40：数学类 =====
  { id: 'c82', bookId: 37, userId: 'u82', userName: '理工学生', content: '同济高数是经典教材，讲解清晰，例题丰富。', createdAt: '2025-03-01T10:00:00Z' },
  { id: 'c83', bookId: 37, userId: 'u83', userName: '考研党', content: '考研必备，函数极限导数积分都讲得很透彻。', createdAt: '2025-02-10T14:30:00Z' },
  { id: 'c84', bookId: 38, userId: 'u84', userName: '数学专业', content: '下册的多元微积分和级数部分，配合习题效果更好。', createdAt: '2025-04-15T09:15:00Z' },
  { id: 'c85', bookId: 38, userId: 'u85', userName: '工科学生', content: '空间解析几何和向量代数讲得很好，适合理工科。', createdAt: '2025-03-20T15:45:00Z' },
  { id: 'c86', bookId: 39, userId: 'u86', userName: '线性代数入门', content: '矩阵和向量空间讲得清晰，是计算机专业的必修。', createdAt: '2025-05-10T11:20:00Z' },
  { id: 'c87', bookId: 39, userId: 'u87', userName: '算法学习者', content: '线性代数是机器学习的基础，这本书很适合入门。', createdAt: '2025-04-05T16:00:00Z' },
  { id: 'c88', bookId: 40, userId: 'u88', userName: '统计学生', content: '概率论与数理统计的理论与应用兼顾，很实用。', createdAt: '2025-06-20T10:30:00Z' },
  { id: 'c89', bookId: 40, userId: 'u89', userName: '数据分析', content: '适合金融、保险、数据分析专业学习。', createdAt: '2025-05-15T13:45:00Z' },

  // ===== 书籍41-42：英语类 =====
  { id: 'c90', bookId: 41, userId: 'u90', userName: '大学英语', content: '第一册的主题贴近大学生生活，词汇语法练习全面。', createdAt: '2025-03-25T09:00:00Z' },
  { id: 'c91', bookId: 41, userId: 'u91', userName: '英语学习者', content: '高校公共英语必修教材，听说读写综合训练。', createdAt: '2025-02-15T14:15:00Z' },
  { id: 'c92', bookId: 42, userId: 'u92', userName: '英语进阶', content: '第二册主题更丰富，科技环保社会热点都有。', createdAt: '2025-04-30T11:30:00Z' },
  { id: 'c93', bookId: 42, userId: 'u93', userName: '跨文化交际', content: '帮助拓展视野，提升跨文化交际能力。', createdAt: '2025-03-10T16:20:00Z' },

  // ===== 书籍43-53：计算机类 =====
  { id: 'c94', bookId: 43, userId: 'u94', userName: 'C语言入门', content: '谭浩强的书通俗易懂，是C语言入门的首选。', createdAt: '2025-05-05T10:15:00Z' },
  { id: 'c95', bookId: 43, userId: 'u95', userName: '编程新手', content: '例题丰富实用，指针部分讲得特别好。', createdAt: '2025-04-10T15:00:00Z' },
  { id: 'c96', bookId: 44, userId: 'u96', userName: '计算机专业', content: '严蔚敏的数据结构是必修课的核心教材。', createdAt: '2025-06-15T09:30:00Z' },
  { id: 'c97', bookId: 44, userId: 'u97', userName: '算法基础', content: '线性表、树、图都讲得很清楚，配有C代码。', createdAt: '2025-05-20T14:45:00Z' },
  { id: 'c98', bookId: 45, userId: 'u98', userName: '硬件爱好者', content: '计算机组成原理帮助理解底层工作机制。', createdAt: '2025-07-25T11:00:00Z' },
  { id: 'c99', bookId: 45, userId: 'u99', userName: '系统理解', content: 'CPU、存储、IO系统讲得全面，软硬件协同很重要。', createdAt: '2025-06-05T16:30:00Z' },
  { id: 'c100', bookId: 46, userId: 'u100', userName: '操作系统', content: '进程内存文件管理讲得透彻，结合实例分析。', createdAt: '2025-08-10T10:20:00Z' },
  { id: 'c101', bookId: 46, userId: 'u101', userName: '系统开发', content: '理解操作系统如何协调资源，是核心必修课。', createdAt: '2025-07-15T15:45:00Z' },
  { id: 'c102', bookId: 47, userId: 'u102', userName: '网络工程师', content: '谢希仁的网络教材从OSI到TCP/IP讲得很系统。', createdAt: '2025-03-20T09:15:00Z' },
  { id: 'c103', bookId: 47, userId: 'u103', userName: '协议学习', content: 'HTTP、DNS等协议讲解清晰，适合网络专业。', createdAt: '2025-02-25T14:00:00Z' },
  { id: 'c104', bookId: 48, userId: 'u104', userName: '数据库入门', content: '王珊的数据库教材理论实务兼顾，SQL讲得好。', createdAt: '2025-04-25T11:30:00Z' },
  { id: 'c105', bookId: 48, userId: 'u105', userName: '数据管理', content: '事务并发恢复技术，是理解数据存储的核心。', createdAt: '2025-03-30T16:15:00Z' },
  { id: 'c106', bookId: 49, userId: 'u106', userName: 'Java学习者', content: 'Java编程基础教材，面向对象讲得很清晰。', createdAt: '2025-05-30T10:45:00Z' },
  { id: 'c107', bookId: 49, userId: 'u107', userName: '编程入门', content: '案例丰富，适合零基础入门Java。', createdAt: '2025-04-20T15:20:00Z' },
  { id: 'c108', bookId: 50, userId: 'u108', userName: '软件工程', content: '软件生命周期和项目管理讲得全面。', createdAt: '2025-06-25T09:00:00Z' },
  { id: 'c109', bookId: 50, userId: 'u109', userName: '开发流程', content: '理解工程化开发规范，是计算机专业必修。', createdAt: '2025-05-10T14:30:00Z' },
  { id: 'c110', bookId: 51, userId: 'u110', userName: 'Python入门', content: '项目驱动学习方式，三个实战项目很有价值。', createdAt: '2025-07-20T11:15:00Z' },
  { id: 'c111', bookId: 51, userId: 'u111', userName: '自学编程', content: '从基础到Web开发数据可视化，适合零基础。', createdAt: '2025-06-10T16:00:00Z' },
  { id: 'c112', bookId: 52, userId: 'u112', userName: 'Vue开发者', content: '霍春阳的书深入剖析Vue内部机制，进阶必读。', createdAt: '2025-08-05T10:30:00Z' },
  { id: 'c113', bookId: 52, userId: 'u113', userName: '前端进阶', content: '响应式系统和虚拟DOM的原理讲得很透彻。', createdAt: '2025-07-10T15:45:00Z' },
  { id: 'c114', bookId: 53, userId: 'u114', userName: '算法学习', content: '算法导论是权威教材，理论严谨内容全面。', createdAt: '2025-03-15T09:20:00Z' },
  { id: 'c115', bookId: 53, userId: 'u115', userName: '程序员进阶', content: '排序搜索图算法动态规划，程序员必读经典。', createdAt: '2025-02-05T14:45:00Z' },

  // ===== 书籍54-57：思想政治类 =====
  { id: 'c116', bookId: 54, userId: 'u116', userName: '思政学生', content: '马克思主义基本原理概论，世界观方法论讲得系统。', createdAt: '2025-04-10T10:00:00Z' },
  { id: 'c117', bookId: 54, userId: 'u117', userName: '理论学习', content: '哲学政治经济学科学社会主义，三大组成部分全面。', createdAt: '2025-03-15T15:30:00Z' },
  { id: 'c118', bookId: 55, userId: 'u118', userName: '毛概学习', content: '毛泽东思想的形成发展和核心内容讲解清晰。', createdAt: '2025-05-20T11:15:00Z' },
  { id: 'c119', bookId: 55, userId: 'u119', userName: '历史经验', content: '理解中国革命与建设的历史经验，思政必修。', createdAt: '2025-04-25T16:00:00Z' },
  { id: 'c120', bookId: 56, userId: 'u120', userName: '思修学习', content: '理想信念爱国主义道德法律基础都涵盖。', createdAt: '2025-06-15T10:45:00Z' },
  { id: 'c121', bookId: 56, userId: 'u121', userName: '价值观', content: '帮助树立正确的人生观价值观道德观法治观。', createdAt: '2025-05-05T15:20:00Z' },
  { id: 'c122', bookId: 57, userId: 'u122', userName: '近代史', content: '从鸦片战争至今的历史脉络梳理清晰。', createdAt: '2025-07-25T09:30:00Z' },
  { id: 'c123', bookId: 57, userId: 'u123', userName: '历史规律', content: '重大历史事件讲解，帮助理解历史发展规律。', createdAt: '2025-06-20T14:15:00Z' },

  // ===== 书籍58-60：自然科学类 =====
  { id: 'c124', bookId: 58, userId: 'u124', userName: '物理学生', content: '力学热学讲得严谨，例题丰富适合理工科。', createdAt: '2025-03-25T11:00:00Z' },
  { id: 'c125', bookId: 58, userId: 'u125', userName: '大学物理', content: '运动学动力学振动波热力学都涵盖。', createdAt: '2025-02-10T16:30:00Z' },
  { id: 'c126', bookId: 59, userId: 'u126', userName: '电磁学', content: '静电场磁场电磁感应讲得很透彻。', createdAt: '2025-04-30T10:15:00Z' },
  { id: 'c127', bookId: 59, userId: 'u127', userName: '光学学习', content: '干涉衍射偏振，承接上册是进阶教材。', createdAt: '2025-03-20T15:45:00Z' },
  { id: 'c128', bookId: 60, userId: 'u128', userName: '化学学生', content: '物质结构化学反应原理讲得清晰。', createdAt: '2025-05-15T09:00:00Z' },
  { id: 'c129', bookId: 60, userId: 'u129', userName: '化工专业', content: '电化学有机化学基础，配合实验案例。', createdAt: '2025-04-05T14:20:00Z' },

  // ===== 书籍61-64：经管类 =====
  { id: 'c130', bookId: 61, userId: 'u130', userName: '管理学学生', content: '周三多的管理学原理，计划组织领导控制全面。', createdAt: '2025-06-05T11:30:00Z' },
  { id: 'c131', bookId: 61, userId: 'u131', userName: '企业管理', content: '结合企业案例，理解现代管理理论与实践。', createdAt: '2025-05-10T16:00:00Z' },
  { id: 'c132', bookId: 62, userId: 'u132', userName: '经济学入门', content: '曼昆的经济学原理语言通俗案例丰富。', createdAt: '2025-07-15T10:45:00Z' },
  { id: 'c133', bookId: 62, userId: 'u133', userName: '微观宏观', content: '供需理论市场均衡国民收入增长都涵盖。', createdAt: '2025-06-25T15:20:00Z' },
  { id: 'c134', bookId: 63, userId: 'u134', userName: '营销学生', content: '科特勒的市场营销学，产品定价渠道促销全面。', createdAt: '2025-08-10T09:15:00Z' },
  { id: 'c135', bookId: 63, userId: 'u135', userName: '品牌管理', content: '结合大量企业营销案例，营销专业必修。', createdAt: '2025-07-20T14:30:00Z' },
  { id: 'c136', bookId: 64, userId: 'u136', userName: '会计入门', content: '葛家澍的会计学基础，理论与实务结合。', createdAt: '2025-03-10T11:00:00Z' },
  { id: 'c137', bookId: 64, userId: 'u137', userName: '财务管理', content: '会计要素复式记账财务报表都讲得清楚。', createdAt: '2025-02-15T16:15:00Z' }
]

/**
 * 根据书籍ID获取书评列表
 * 
 * @param bookId - 书籍ID
 * @returns 书评数组，按时间降序排列（最新评论在前）
 * 
 * 数据来源：
 * 1. 用户评论（存储在localStorage的'comments'键）
 * 2. 模拟评论（mockComments数组）
 * 
 * 用户评论优先显示，两者合并后按时间排序
 */
export function getCommentsByBookId(bookId) {
  const id = Number(bookId)
  // 从模拟数据中筛选该书的评论
  const mockList = mockComments.filter(c => Number(c.bookId) === id)
  // 从localStorage获取用户评论
  const userComments = []
  try {
    const allComments = JSON.parse(localStorage.getItem('comments') || '[]')
    const userList = allComments.filter(c => Number(c.bookId) === id)
    userComments.push(...userList)
  } catch (e) {
    // 解析失败时忽略，不影响功能
  }
  // 合并数据：用户评论在前，模拟评论在后，按时间降序排列
  return [...userComments, ...mockList].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
}