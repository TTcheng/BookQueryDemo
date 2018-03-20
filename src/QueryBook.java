
import java.sql.*;          //包含实现java程序与操作系统以及其他java程序之间进行数据交换的类
import java.awt.*;          //包含创建用户界面与绘制图形的类
import java.awt.event.*;    //事件处理所在的类
import javax.swing.*;       //包含轻量级组件

// 查询图书
public class QueryBook implements ActionListener {
    /**
     * 输入关键字查询图书名中包含关键字的图书
     */
                            //QueryBook类实现接口ActionListener
    JFrame f;               //设置顶级窗口对象
    Container cp;           //容器对象
    JPanel jpS, jpanelWest; //创建面板
    JButton jbt1, jbt2, jbt3;//设置按钮
    JLabel label, L;        //创建标签
    JTextField tf;          //创建文本框
    JTable table;
    Object columnName[] = {"图书名", "图书号", "单价", "作者", "出版社", "入库时间"};
    Object ar[][] = new Object[2000][6];
    String sno;//创建表 与  数组
    String count = "0";

    QueryBook() {
        f = new JFrame("书籍是人类进步的阶梯!");  //设置标题
        cp = f.getContentPane();                        //返回ContentPane内容
        jpS = new JPanel();                             //创建面板jpS
        jpanelWest = new JPanel();                      //创建面板jpanelWest
        jbt1 = new JButton("查询");
        jbt1.setBackground(Color.lightGray);            //创建按钮 设为查询 并且设置背景颜色
        jbt2 = new JButton("确定");
        jbt2.setBackground(Color.lightGray);            //创建按钮 设为确定 并且设置背景颜色
        jbt3 = new JButton("取消");
        jbt3.setBackground(Color.lightGray);            //创建按钮 设为取消 并且设置背景颜色
        label = new JLabel("<html><font color=blue size='6'>图书查询<br><hr>请输入图书名:</Font>", SwingConstants.CENTER);
        label.setForeground(Color.gray);
        //设置超级文本 设置颜色
        L = new JLabel("该种图书共有" + count + "本");//添加标签,显示多少本书
        table = new JTable(ar, columnName);                 //建立一个新的表
        JScrollPane scrollpane = new JScrollPane(table);    //建立一个滚动面板
        tf = new JTextField(18);                  //设置文本框大小
        jpS.add(jbt1);
        jpS.add(jbt2);
        jpS.add(jbt3);                                      //按钮jbt1 jbt2  jbt3 添加到面板jpS
        JPanel jpanel = new JPanel();
        jpanel.add(label);
        jpanel.add(tf);
        JPanel pp4 = new JPanel();
        JPanel jpE = new JPanel();
        cp.add(jpanel, "North");                //面板添加到容器North
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());                    //面板为边界布局
        p.add(L, "North");                      //标签为North方向
        p.add(scrollpane);                                  //面板添加滚动面板
        cp.add(pp4, "West");
        cp.add(p, "Center");
        cp.add(jpS, "South");
        cp.add(jpE, "East");                    //pp4->Westp->Center jpS->South jpE->East
        jpS.setBackground(Color.lightGray);                 //设置面板背景颜色
        jpanel.setBackground(Color.lightGray);              //设置面板背景颜色
        f.setSize(1000, 550);                //设置大小
        f.setVisible(true);                                 //设置可视化
        jbt1.addActionListener(this);
        jbt2.addActionListener(this);
        jbt3.addActionListener(this);                   //按钮监听事件

        showRecord("");         //查询全部select * from book where "图书名"='%%';
    }

    int i = 0;

    public void showRecord(String ql) {
        while (i >= 0) {
            ar[i][0] = "";
            ar[i][1] = "";
            ar[i][2] = "";
            ar[i][3] = "";
            ar[i][4] = "";
            ar[i][5] = "";
            i--;
        }
        i = 0;
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动程序失败");
        }
        try {
            String url = "jdbc:postgresql://127.0.0.1:5432/library";
            String dbuser = "library_user_one";
            String dbpwd = "123456";
            Connection con = DriverManager.getConnection(url, dbuser, dbpwd);
            Statement sql;
            String s = "SELECT * FROM book WHERE \"图书名\" LIKE '%" + ql + "%';";
            sql = con.createStatement();
            ResultSet rs = sql.executeQuery(s);
            while (rs.next()) {
                String bname = rs.getString(1);
                String bno = rs.getString(2);
                String price = rs.getString(3);
                String writer = rs.getString(4);
                String publish = rs.getString(5);
                String indate = rs.getString(6);
                //定义图书名 图书号 价格 作者 出版社 出版日期
                ar[i][0] = bname;
                ar[i][1] = bno;
                ar[i][2] = price;
                ar[i][3] = writer;
                ar[i][4] = publish;
                ar[i][5] = indate;
                i++;
            }
            count = "" + i + "";
            L.setText("书库现有该图书" + count + "本");
            f.repaint();
            con.close();
            System.out.println(ar[0][1]);
        } catch (SQLException g) {
            System.out.println(g.getErrorCode());
            System.out.println(g.getMessage());
        }
    }

    public void deleteRecord(int index) throws InstantiationException, IllegalAccessException {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动程序失败");
        }
        try {
            String dbuser = "library_user_one";
            String dbpwd = "123456";
            String url = "jdbc:postgresql://127.0.0.1:5432/library";
            Connection con = DriverManager.getConnection(url, dbuser, dbpwd);
            Statement sql;
            String ql = (String) (ar[index][1]);
            String s = "delete * from libary.book  where 图书名 ='" + ql + "'";
            sql = con.createStatement();
            int del = sql.executeUpdate(s);
            if (del == 1) {
                JOptionPane.showMessageDialog(null, "删除成功", "信息", JOptionPane.YES_NO_CANCEL_OPTION);
            }
            con.close();
            f.repaint();
        } catch (SQLException g) {
            System.out.println(g.getErrorCode());
            System.out.println(g.getMessage());
        }
    }

    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {
        String ql = "";
        String cmd = e.getActionCommand();
        if (cmd.equals("查询")) {
            ql = tf.getText().trim();
            showRecord(ql);
        }
        if (cmd.equals("确定")) {
            System.exit(0);
        }
        if (cmd.equals("取消"))
            f.hide();
    }

    public static void main(String[] arg) {
        new QueryBook();
    }
}