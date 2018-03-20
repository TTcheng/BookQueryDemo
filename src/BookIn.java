//package Test;
import java.sql.*;//包含实现java程序与操作系统以及其他java程序之间进行数据交换的类

import java.awt.*;//包含创建用户界面与绘制图形的类

import java.awt.event.*;//事件处理所在的类

import java.awt.Container;//包含容器类

import javax.swing.*;//包含轻量级组件

public class BookIn implements ActionListener{

    //BookIn类实现接口ActionListener

    JFrame f3;//设置顶级窗口对象

    Container cp;//容器对象

    JPanel jp1,jp2,jp3,jp4,jp,jpanelWest;//创建面板

    JButton jbt1,jbt2;//设置按钮

    JLabel label1,label2,label3,label4,label;//创建标签

    String sno;//创建字符串对象

    JTextField tf1,tf2,tf3,tf4,tf5,tf6;//创建文本框

    BookIn()//BookIn类

    {

        f3=new JFrame("图书信息添加");//设置标题

        cp=f3.getContentPane();//返回ContentPane内容

        jp1=new JPanel();//面板jp1

        jp2=new JPanel();//面板jp2

        jp3=new JPanel();//面板jp3

        jp4=new JPanel();//面板jp4

        jpanelWest=new JPanel();//创建面板jpanelWest

        jp=new JPanel();

        jbt1=new JButton("确定");

        jbt2=new JButton("取消");//创建按钮 内容分别为确定  取消

        label=new JLabel("<html><font color=blue size='6'>新书登记</font>",SwingConstants.CENTER);

        //创建标签  放到中间



        label.setForeground(Color.black);//标签设置前景色

        tf1=new JTextField(20);//设置文本框tf1的大小

        tf2=new JTextField(20);//设置文本框tf2的大小

        tf3=new JTextField(20);//设置文本框tf3的大小

        tf4=new JTextField(20);//设置文本框tf4的大小

        tf5=new JTextField(20);//设置文本框tf5的大小

        tf6=new JTextField(20);//设置文本框tf6的大小

        jp1.add(jbt1);

        jp1.add(jbt2);

        JPanel jpanel=new JPanel();

        jpanel.add(label);//面板添加标签 label

        JPanel pp4=new JPanel();

        JPanel jpane4=new JPanel();

        cp.add(jpanel,"North");//面板放置North

        JPanel pp2=new JPanel(new GridLayout(6,1,20,10));//设置为网格布局 容器的水平间距为20 垂直布局间距为10

        JPanel pp3=new JPanel();

        pp4.setLayout(new GridLayout(6,1));//设置容器6行1列

        pp4.add(new JLabel("图书名",SwingConstants.CENTER));

        pp2.add(tf1);

        pp4.add(new JLabel("图书号",SwingConstants.CENTER));

        pp2.add(tf2);

        pp4.add(new JLabel("出版时间",SwingConstants.CENTER));

        pp2.add(tf3);

        pp4.add(new JLabel("出版社",SwingConstants.CENTER));

        pp2.add(tf4);

        pp4.add(new JLabel("价格",SwingConstants.CENTER));

        pp2.add(tf5);

        pp4.add(new JLabel("入库时间",SwingConstants.CENTER));

        pp2.add(tf6);

        //以上分别为将图书名 图书号 单价 作者 出版社 入库时间添加到pp4面板中

        //以上分别为将文本框添加到pp2面板中

        pp3.add(jbt1);

        pp3.add(jbt2);//  分别将按钮添加到面板pp3中

        cp.add(pp4,"West");

        cp.add(pp2,"East");

        cp.add(pp3,"South");

        cp.add(jpane4,"Center");

        //容器中West->pp4 East->pp2 South->pp3Center->jpane4 North->jpanel

        f3.setSize(320,300);//设置顶级窗口大小

        f3.setVisible(true);//设置顶级窗口可视化

        jbt1.addActionListener(this);
        jbt2.addActionListener(this);

    }

    public void insertRecord() throws InstantiationException, IllegalAccessException

    {

        if(tf1.getText().equals("")||tf2.getText().equals("")||tf3.getText().equals("")||

                tf4.getText().equals("")||tf5.getText().equals("")||tf6.getText().equals(""))

        {

            JOptionPane.showMessageDialog(f3,"请填写图书资料");

            return;

        }//进行图书添加

        try

        {

            Class.forName("org.postgresql.Driver").newInstance();

        }//连接数据库

        catch(ClassNotFoundException e)

        {

            System.out.println("加载驱动程序出错");

        }

        try

        {

            String dbuser = "postgres";

            String dbpwd= "19960314";

            String url="jdbc:postgresql://127.0.0.1:5432/library";

            Connection con=DriverManager.getConnection(url,dbuser,dbpwd);

            Statement sql;

            String s="insert into book values('"+tf1.getText()+"','"+tf2.getText()+"','"+tf3.getText()+"','"

                    +tf4.getText()+"','"+tf5.getText()+"','"+tf6.getText()+"');";

            String query="select * from book where \"图书号\"='"+tf2.getText()+"';";

            //查询功能

            sql=con.createStatement();

            ResultSet rs=sql.executeQuery(query);

            boolean moreRecords=rs.next();//以上数据库操作基本上一样

            if(moreRecords)

            {

                JOptionPane.showMessageDialog(f3,"图书号已经被使用,请重新输入");

                con.close();//判断图书号是否已经被用

                tf2.setText("");//重置图书号

                return;

            }

            int insert=sql.executeUpdate(s);

            if(insert==1)

            {



                JOptionPane.showMessageDialog(null,"图书信息录入成功!!!");

                con.close();

                tf1.setText("");

                tf2.setText("");

                tf3.setText("");

                tf4.setText("");

                tf5.setText("");

                tf6.setText("");//更新数据库,并且重置六个文本框

            }

        }

        catch(SQLException g)

        {

            System.out.println(g.getErrorCode());

            System.out.println(g.getMessage());

        }

    }

    @SuppressWarnings("deprecation")@Override

    public void actionPerformed(ActionEvent e) {



        String cmd=e.getActionCommand();

        if(cmd.equals("确定"))

        {

            try {
                insertRecord();
            } catch (InstantiationException | IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }//匹配按钮事件,调用插入方法

        }

        else if(cmd.equals("取消"))

        {

            f3.hide();//退出窗口

        }

    }

    public static void main(String [] args)

    {

        new BookIn();

    }

}