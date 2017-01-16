package com.koalitech.service.Database;


/**
 * Created by zoson on 7/20/15.
 */
public class CreateTable {
    public static final String DATABASE_NAME = "BSmart";

    public static final String CHATLOG = "create table "+ UserChatTable.TABLE_NAME+"("
            + UserChatTable.ID + " INTEGER,"
            + UserChatTable.CONTENT + " TEXT)";
    public static final String USER = "create table "+ UserInfoTable.TABLE_NAME+"("
            + UserInfoTable.RID + " INTEGER,"
            + UserInfoTable.CONTENT + " TEXT)";

    public class UserChatTable{
        public static final String TABLE_NAME = "chatlog";
        public static final String ID = "id";
        public static final String CONTENT = "content";
    }

    public class UserInfoTable{
        public static final String TABLE_NAME = "user";
        public static final String RID = "rid";
        public static final String CONTENT = "name";
    }
}
