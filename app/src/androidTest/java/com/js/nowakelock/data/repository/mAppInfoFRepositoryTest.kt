package com.js.nowakelock.data.repository

class mAppInfoFRepositoryTest {
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var aR: IAppInfoRepository
//    private lateinit var db: AppDatabase
//
//    @Before
//    fun setUp() {
////        val context = ApplicationProvider.getApplicationContext<Context>()
////        db = Room.inMemoryDatabaseBuilder(
////            context, AppDatabase::class.java
////        ).build()
////        aR =
////            IAppInfoRepository(
////                db.appInfoDao()
////            )
//    }
//
//    @After
//    fun tearDown() {
//        db.close()
//    }
//
//    @Test
//    fun getAppLists() {
//        val appInfos = aR.getAppLists()
//        assertTrue(LiveDataTestUtil.getValue(appInfos).isEmpty())
//        runBlocking { db.appInfoDao().insert(TestData.appInfos) }
//
//        assertEquals(LiveDataTestUtil.getValue(appInfos).size, 10)
//    }
//
//    @Test
//    fun sync() {
//        val appInfos = aR.getAppLists()
//        assertTrue(LiveDataTestUtil.getValue(appInfos).isEmpty())
//        runBlocking { aR.syncAppInfos() }
//        assertTrue(LiveDataTestUtil.getValue(appInfos).isNotEmpty())
//    }
//
//    @Test
//    fun saveAppSetting() {
////        val appInfoSTs = aR.getAppSetting(TestData.appInfoST.packageName)
////        assertTrue(LiveDataTestUtil.getValue(appInfoSTs) == null)
////        runBlocking { aR.saveAppSetting(TestData.appInfoST) }
////        assertEquals(
////            LiveDataTestUtil.getValue(appInfoSTs),
////            TestData.appInfoST
////        )
////        assertTrue(LiveDataTestUtil.getValue(appInfoSTs).isNotEmpty())
//    }
//
//    companion object {
//        @Volatile
//        private var instance: IAppInfoRepository? = null
//        fun getInstance(appInfoDao: AppInfoDao) =
//            instance ?: synchronized(this) {
//                instance ?: IAppInfoRepository(
//                    appInfoDao
//                ).also { instance = it }
//            }
//    }
}