package com.example.jreg0;

import com.example.jreg0.stopstation.StopStationRepository;
import com.example.jreg0.train.TrainRepository;
import com.example.jreg0.train.TrainService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

class TrainServiceTests {

    private Connection connection;
    private Statement stmt;


//    @InjectMocks
//    private TrainService trainService;
//
//    @Mock
//    private TrainRepository trainRepository;
//
//    @BeforeAll
//    static void beforeAll() {
//        List<ScheduleEntity> scheduleEntity = new List<ScheduleEntity>[];
//        TrainEntity trainEntity = new TrainEntity("00000000", "0000", "hayate-0", "000000", "hayate", 12,s);
//    }

    @BeforeEach
    public void setUp() throws SQLException {
        // H2インメモリデータベースへの接続
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        stmt = connection.createStatement();

        // テーブルの作成
        String createTrainTableQuery = "CREATE TABLE train(id VARCHAR(36) PRIMARY KEY,train_number VARCHAR(255),train_name VARCHAR(255),route_id VARCHAR(12),train_nickname VARCHAR(255),formation INT,FOREIGN KEY (route_id) REFERENCES route (id))";
        String createScheduleTableQuery = "CREATE TABLE schedule(id VARCHAR(36) PRIMARY KEY,train_id VARCHAR(36),station_id VARCHAR(36),departure_time time,arrival_time time,departure_track INT,departure_date date,FOREIGN KEY (train_id) REFERENCES train (id),FOREIGN KEY (station_id) REFERENCES station (id))";
        String createRouteTableQuery = "CREATE TABLE route(id VARCHAR(36) PRIMARY KEY,route_name VARCHAR (255))";
        String createStopStationQuery = "CREATE TABLE stopstation(route_id VARCHAR(36),station_id VARCHAR(36),PRIMARY KEY (route_id, station_id),FOREIGN KEY (route_id) REFERENCES route (id),FOREIGN KEY (station_id) REFERENCES station (id))";
        String createStationQuery = "CREATE TABLE station(id VARCHAR(36) PRIMARY KEY,station_name VARCHAR(255))";
        stmt.execute(createStationQuery);
        stmt.execute(createRouteTableQuery);
        stmt.execute(createStopStationQuery);
        stmt.execute(createTrainTableQuery);
        stmt.execute(createScheduleTableQuery);

    }

    @Test
    public void testInsertAndSelect() throws SQLException {
        // データの挿入
        String insertQuery = "INSERT INTO train(id, train_number, train_name, route_id, train_nickname, formation)values ('00000000', '0000', 'hayate-0', '00000000', 'hayate', 12)";
        String insertRouteQuery = "INSERT INTO route(id, route_name)values ('00000000', '東北')";

        stmt.execute(insertRouteQuery);
        stmt.execute(insertQuery);



        // データの取得
        String selectQuery = "SELECT * FROM train";
        ResultSet resultSet = stmt.executeQuery(selectQuery);
        Date date = new Date(2026,6,3);
        TrainRepository trainRepository;
        StopStationRepository stopStationRepository;

        TrainService trainService = null;
        trainService.getTrainByStation("00000000", "00000001", date);
//        // 結果を確認
//        Assertions.assertTrue(resultSet.next());
//        Assertions.assertEquals("Alice", resultSet.getString("name"));
//        Assertions.assertEquals("alice@example.com", resultSet.getString("email"));
    }

    //
//    @Test
//    void testGetTrainByStation() {
//        System.out.println(trainService.getTrainByStation("00000000", "00000001", new Date("2026-06-03")));
//    }
    @AfterEach
    public void tearDown() throws SQLException {
        // 接続のクローズ
        stmt.close();
        connection.close();
    }
}
