package com.study.spring.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {

    void setter(PreparedStatement pstmt) throws SQLException;
}
