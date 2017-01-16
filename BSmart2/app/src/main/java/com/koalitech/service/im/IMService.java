package com.koalitech.service.im;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.koalitech.utils.ThreadPool;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zoson on 4/19/15.
 */
public class IMService implements ChatManagerListener,ChatMessageListener,FileTransferListener{
    private final static String TAG = "IMService";
    private XMPPTCPConnectionConfiguration connectionConfiguration;
    private XMPPTCPConnection connection;
    public static String xmpp_host = "120.25.12.205";//  119.29.22.244 120.25.12.205
    private static int xmpp_port = 5222;
    public static String xmpp_service_name = "120.25.12.205";
    public static  String resource = "Spark";
    private Map<String,IMListener> listensers;
    private IMListener systemListener;
    private IMListener grobalListener;
    private ChatManager chatManager;
    private FileTransferManager fileTransferManager;
    private AccountManager accountManager;
    private OfflineMessageManager offlineMessageManager;
    private Roster roster;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath();

    public final static int OP = 0x7;
    public final static int MSG = 0x8;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what){
                case OP:
                    OpResultInfo opResultInfo = (OpResultInfo)msg.obj;
                    if (opResultInfo==null)return;
                    if (opResultInfo.imCallback==null)return;
                    opResultInfo.imCallback.opResult(opResultInfo.state,opResultInfo.result);
                    break;
                case MSG:
                    notifyListenersLock((Msg)msg.obj);
                    break;
            }

        }
    };

    public IMService(){
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setServiceName(xmpp_service_name);
        builder.setHost(xmpp_host);
        builder.setPort(xmpp_port);
        builder.setSendPresence(false);
        builder.setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled);
        builder.setDebuggerEnabled(true);
        builder.setCompressionEnabled(true);
        connectionConfiguration = builder.build();
        SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
        connection = new XMPPTCPConnection(connectionConfiguration);

        accountManager = AccountManager.getInstance(connection);
        fileTransferManager = FileTransferManager.getInstanceFor(connection);
        offlineMessageManager = new OfflineMessageManager(connection);

        listensers = new HashMap<>();
    }

    public void setPath(String path){
        this.path = path;
    }

    public void connect(final String account,final String password, final IMCallback callback){
        ThreadPool.start(new LoginRunable(account, password, callback));
    }

    public void addListener(String account,IMListener imListener){
        listensers.put(account, imListener);
    }

    public void addGrobalListener(IMListener imListener){
        this.grobalListener = imListener;
    }

    public void rmListener(String listener){
        listensers.remove(listener);
    }

    public void addBroadcast(IMListener imListener){
        this.systemListener = imListener;
    }

    public void rmAllListeners(){
        listensers.clear();
    }

    protected synchronized void notifyListenersLock(Msg message){
        Message msg = message.message;
        if (msg==null)return;
        String sender  = msg.getFrom().split("@")[0];
        String content = msg.getBody();
        if (sender.equals("admin")){
            systemListener.getMessage(message.type,sender,content);
        }else{
            IMListener imListener = listensers.get(sender);
            if (imListener!=null){
                imListener.getMessage(message.type,sender,content);
            }else if (grobalListener!=null){
                grobalListener.getMessage(message.type,sender,content);
            }
        }
    }

    public void sendMessage(String user,String content,IMCallback imCallback){
        ThreadPool.start(new SendTxtRunnable(user, content, imCallback));
    }

    public void sendFile(String user,String filepath,IMCallback imCallback){
        ThreadPool.start(new SendFileRunnable(user, filepath, imCallback));
    }

    public void disConnect(){
        connection.disconnect();
    }

    @Override
    public void chatCreated(Chat chat, boolean b) {
        chat.addMessageListener(this);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        Log.i(TAG, "接收到::" + message.getBody());
        processMessage(message);
    }

    public void processMessage(Message message) {
        if (message.getBody()==null||message.equals("")){
            return;
        }
        android.os.Message  handlemessage = android.os.Message.obtain();
        Msg msg = new Msg();
        msg.type = IMListener.TXT;
        msg.message = message;
        handlemessage.obj = msg;
        handlemessage.what = MSG;
        handler.sendMessage(handlemessage);
    }

    public String addXmppHost(String user){
        return user+"@"+xmpp_host+"/"+resource;
    }

    public void regXmpp( String account, String password, String username, IMCallback callback){
        ThreadPool.start(new RegisterRunnable(account, password, username, callback));
    }

    //文件接收
    @Override
    public void fileTransferRequest(FileTransferRequest fileTransferRequest) {
        //System.out.println("fileTransfer文件接收开始main Thread");
        ThreadPool.start(new RecFileRunnable(fileTransferRequest));
    }

    public boolean isConnected(){
        return connection.isConnected();
    }

    class LoginRunable implements Runnable{
        private String account;
        private String password;
        private IMCallback imCallback;
        public LoginRunable(String account,String password,IMCallback imCallback){
            this.account = account;
            this.password = password;
            this.imCallback = imCallback;
        }
        @Override
        public void run() {
            android.os.Message message = android.os.Message.obtain();
            OpResultInfo opResultInfo = new OpResultInfo();
            opResultInfo.imCallback  = imCallback;
            connection = new XMPPTCPConnection(connectionConfiguration);

            try{
                try {
                    connection.connect();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (XMPPException e){
                opResultInfo.state = IMCallback.FAIL;
                opResultInfo.result = "服务器错误";
                e.printStackTrace();
            }

            try {
                try {
                    connection.login(account,password,resource);
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                处理离线消息
                 */
//                List<Message> list = null;
//                try {
//                    list = offlineMessageManager.getMessages();
//                } catch (SmackException.NoResponseException e) {
//                    e.printStackTrace();
//                } catch (SmackException.NotConnectedException e) {
//                    e.printStackTrace();
//                }
//                Map<String,ArrayList<Message>> offlineMsgs = new HashMap<>();
//                Iterator<Message> it = list.iterator();
//                while(it.hasNext()) {
//                    Message msg = it.next();
//                    processMessage(msg);
//                }
//                try {
//                    offlineMessageManager.deleteMessages();
//                } catch (SmackException.NoResponseException e) {
//                    e.printStackTrace();
//                } catch (SmackException.NotConnectedException e) {
//                    e.printStackTrace();
//                }

                Presence presence = new Presence(Presence.Type.available);
                try {
                    connection.sendPacket(presence);//上线了
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            }catch (IllegalStateException e){
                opResultInfo.state = IMCallback.FAIL;
                opResultInfo.result = "服务器出错";
                handler.sendMessage(message);
                e.printStackTrace();
                return;
            }catch (XMPPException e){
                opResultInfo.state = IMCallback.FAIL;
                opResultInfo.result = "登录失败";
                e.printStackTrace();
                handler.sendMessage(message);
                return;
            }

            if (connection.isConnected()){
                opResultInfo.state = IMCallback.SUCC;
                opResultInfo.result = "登录成功";
            }
            chatManager = ChatManager.getInstanceFor(connection);
            roster = Roster.getInstanceFor(connection);
            roster.addRosterListener(new UserRoasterListener());
            chatManager.addChatListener(IMService.this);
            fileTransferManager.addFileTransferListener(IMService.this);
            message.obj = opResultInfo;
            message.what = OP;
            handler.sendMessage(message);
        }
    }

    class RegisterRunnable implements Runnable{
        private IMCallback callback;
        private String account;
        private String password;
        private String username;
        private String sex;
        public RegisterRunnable(String account,String password,String username,IMCallback imCallback){
            this.callback = imCallback;
            this.account = account;
            this.password = password;
            this.username = username;
        }
        @Override
        public void run() {
            OpResultInfo opResultInfo = new OpResultInfo();
            opResultInfo.imCallback = callback;
            try {
                try {
                    if (!connection.isConnected()){
                        connection.connect();
                        accountManager = AccountManager.getInstance(connection);
                    }
                } catch (SmackException e) {
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (XMPPException e) {
                    e.printStackTrace();
                    return;
                }
                accountManager.createAccount(account,password);
                opResultInfo.result = "注册成功";
                opResultInfo.state = IMCallback.SUCC;
            } catch (SmackException.NoResponseException e) {
                opResultInfo.state = IMCallback.FAIL;
                e.printStackTrace();
            } catch (XMPPException.XMPPErrorException e) {
                opResultInfo.state = IMCallback.FAIL;
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                opResultInfo.state = IMCallback.FAIL;
                e.printStackTrace();
            }
            android.os.Message message = android.os.Message.obtain();
            message.obj = opResultInfo;
            message.what = OP;
            handler.sendMessage(message);
        }
    }

    class SendTxtRunnable implements Runnable{

        private String sender;
        private String content;
        private IMCallback imCallback;

        public SendTxtRunnable(String sender,String content,IMCallback imCallback){
            this.sender = sender;
            this.content = content;
            this.imCallback = imCallback;
        }
        @Override
        public void run() {
            android.os.Message message = android.os.Message.obtain();
            OpResultInfo opResultInfo = new OpResultInfo();
            opResultInfo.imCallback = imCallback;
            if (!connection.isConnected()){
                try {
                    try {
                        connection.connect();
                    } catch (SmackException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (XMPPException e) {
                    e.printStackTrace();
                    opResultInfo.result = "网络错误";
                    opResultInfo.state = IMCallback.FAIL;
                    handler.sendMessage(message);
                    return;
                }
            }
            Chat chat = null;
            chat = chatManager.createChat(addXmppHost(sender),null);

            if (chat != null) {
                    System.out.println("send message:" + content + " to " + sender);
                    Log.i(TAG, "send message:" + content + " to " + sender);
                    try {
                        chat.sendMessage(content);
                    } catch (SmackException.NotConnectedException e) {
                        e.printStackTrace();
                        e.printStackTrace();
                        opResultInfo.result = "发送失败";
                        opResultInfo.state = IMCallback.FAIL;
                    }
                    opResultInfo.state = IMCallback.SUCC;
                    opResultInfo.result = "发送成功";
                    message.obj = opResultInfo;
                    message.what = OP;

            }else{
                opResultInfo.result = "用户不存在";
                opResultInfo.state = IMCallback.FAIL;
            }
            handler.sendMessage(message);
        }
    }

    class SendFileRunnable implements Runnable{
        private String sender;
        private String path;
        private IMCallback imCallback;
        public SendFileRunnable(String sender,String path,IMCallback imCallback){
            this.sender = sender;
            this.path = path;
            this.imCallback = imCallback;
        }

        @Override
        public void run() {
            Log.i(TAG,"SendFile Begin");
            android.os.Message message = android.os.Message.obtain();
            OpResultInfo opResultInfo = new OpResultInfo();
            opResultInfo.imCallback = imCallback;
            if (!connection.isConnected()){
                    try {
                        connection.connect();
                    } catch (SmackException e) {
                        e.printStackTrace();
                        opResultInfo.result = "网络错误";
                        opResultInfo.state = IMCallback.FAIL;
                        handler.sendMessage(message);
                        return;
                    } catch (IOException e) {
                        opResultInfo.result = "网络错误";
                        opResultInfo.state = IMCallback.FAIL;
                        handler.sendMessage(message);
                        e.printStackTrace();
                        return;
                    } catch (XMPPException e) {
                        opResultInfo.result = "网络错误";
                        opResultInfo.state = IMCallback.FAIL;
                        handler.sendMessage(message);
                        e.printStackTrace();
                    }
            }
            Presence presence = Roster.getInstanceFor(connection).getPresence(addXmppHost(sender));
            Log.i(TAG, "SendFile Presence = " + presence);
            System.out.println("SendFile Presence = "+presence+" ---------------------");
            //if (presence.getType()!= Presence.Type.unavailable){
            if (presence !=null){
                OutgoingFileTransfer transfer = fileTransferManager.createOutgoingFileTransfer(presence.getFrom());
                    try {
                        transfer.sendFile(new File(path), "语音");
                    } catch (SmackException e) {
                        e.printStackTrace();
                    }
                    while(!transfer.isDone());
                    opResultInfo.result = path;
                    System.out.println("发送成功");
                    opResultInfo.state = IMCallback.SUCC;

            }else{
                opResultInfo.result = "对方不在线";
                opResultInfo.state = IMCallback.FAIL;
            }
            message.obj = opResultInfo;
            message.what = OP;
            handler.sendMessage(message);
            Log.i(TAG, "SendFile end");
        }
    }

    class RecFileRunnable implements Runnable{

        private FileTransferRequest fileTransferRequest;
        public RecFileRunnable(FileTransferRequest fileTransferRequest){
            this.fileTransferRequest = fileTransferRequest;
        }
        @Override
        public void run() {
            Log.i(TAG, "fileTransferRequest::开始接收文件");
            //System.out.println("fileTransferRequest::开始接收文件");
            IncomingFileTransfer incomingFileTransfer = fileTransferRequest.accept();
            long startTime = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fromUser = fileTransferRequest.getRequestor().split("@")[0];
            String fileName = fileTransferRequest.getFileName();
            String dirpath = path+"/"+fromUser;
            String filepath = dirpath + "/it/"+fileName;
            System.out.println("fileTransferRequest filepath ==== "+filepath);
            File file = new File(filepath);
            File dir = new File(dirpath);
            if (!dir.exists()){
                dir.mkdir();
            }
            if (file.exists())file.delete();
            try {
                incomingFileTransfer.recieveFile(file);
            } catch (SmackException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            boolean isRepeat = false;
            while (!incomingFileTransfer.getStatus().equals(FileTransfer.Status.complete)){
                if (incomingFileTransfer.getStatus().equals(FileTransfer.Status.error)){
                    System.out.println(sdf.format(new Date())+"error!!!"+incomingFileTransfer.getError());
                    incomingFileTransfer.cancel();
                    try {
                        if (isRepeat)break;
                        isRepeat = true;
                        incomingFileTransfer.recieveFile(file);
                    } catch (SmackException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    double progress = incomingFileTransfer.getProgress();
                    progress*=100;
                    //System.out.println(sdf.format(new Date())+"status="+incomingFileTransfer.getStatus());
                    //System.out.println(sdf.format(new Date())+"progress="+sdf.format(progress)+"%");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println("used " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds  ");
            Msg msg = new Msg();
            msg.type = IMListener.VOICE;
            Message mess = new Message();
            mess.setFrom(fromUser);
            mess.setBody(filepath);
            msg.message = mess;
            android.os.Message message = android.os.Message.obtain();
            message.what = MSG;
            message.obj = msg;
            handler.sendMessage(message);
            long length = fileTransferRequest.getFileSize();
            Log.i(TAG, "fileTransferRequest::文件大小:" + length + " 文件发起者:" + fileTransferRequest.getRequestor() + " 文件MIME类型:" + fileTransferRequest.getMimeType());
            System.out.println("接收成功");
        }
    }

    class Msg{
        int type;
        Message message;
    }

    class OpResultInfo{
        public int state;
        public String result;
        public IMCallback imCallback;
    }

    public List<String> getAllUsers() {
        Roster roster = Roster.getInstanceFor(connection);
        //List<RosterEntry> EntriesList = new ArrayList<RosterEntry>();
        List<String> names = new ArrayList<>();
        Collection<RosterEntry> rosterEntry = roster.getEntries();
        Iterator<RosterEntry> i = rosterEntry.iterator();
        while (i.hasNext()) {
            String account = i.next().getUser().split("@")[0];
            names.add(account);
        }
        return names;
    }

    public void addUser(String account,String name){
        try {
            roster.createEntry(addXmppHost(account),addXmppHost(name),null);
        } catch (SmackException.NotLoggedInException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void remoteUser(String account){
        try {
            RosterEntry rosterEntry = roster.getEntry(addXmppHost(account));
            roster.removeEntry(rosterEntry);
        } catch (SmackException.NotLoggedInException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    class UserRoasterListener implements RosterListener{

        @Override
        public void entriesAdded(Collection<String> collection) {
            System.out.println("entriesAdded");
            int len = collection.size();
            System.out.println("entries size === "+len);
            Iterator<String> it = collection.iterator();
            for (int i=0;i<len;i++){
                System.out.println("collection content === " +it.next());
            }
        }

        @Override
        public void entriesUpdated(Collection<String> collection) {
            System.out.println("entriesUpdated");
            int len = collection.size();
            System.out.println("entries size === "+len);
            Iterator<String> it = collection.iterator();
            for (int i=0;i<len;i++){
                System.out.println("collection content === " +it.next());
            }
        }

        @Override
        public void entriesDeleted(Collection<String> collection) {
            System.out.println("entriesDeleted");
            int len = collection.size();
            System.out.println("entries size === "+len);
            Iterator<String> it = collection.iterator();
            for (int i=0;i<len;i++){
                System.out.println("collection content === " +it.next());
            }
        }

        @Override
        public void presenceChanged(Presence presence) {
            System.out.println("presenceChange");
        }
    }
}
