package com.aleksey.crud_app.mapping;

import com.aleksey.crud_app.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WriterMapping {

    public static List<Writer> writerMapping(ResultSet resultSet) {
        List<Writer> writerList = new ArrayList<>();
        Writer selectWriter = null;
        int currentWriterId = -1;

        try {
            while (resultSet.next()) {

                int writerId = resultSet.getInt("writer.id");
                if (currentWriterId != writerId) {
                    selectWriter = new Writer();
                    selectWriter.setId(writerId);
                    selectWriter.setFistName(resultSet.getString("writer.fistName"));
                    selectWriter.setLastName(resultSet.getString("writer.lastName"));
                    selectWriter.setStatus(Status.valueOf(resultSet.getString("writer.status")));
                    selectWriter.setPosts(PostMapping.postMapping(resultSet));

                    writerList.add(selectWriter);

                    currentWriterId = writerId;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writerList;
    }
}
