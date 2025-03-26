import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
//验证码随机刷新函数
class CodeUtil {
    public static String getCode () {
        ArrayList<Character> list1 = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list1.add((char) ('a' + i));
            list1.add((char) ('A' + i));
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(list1.size());
            char c = list1.get(index);

            sb.append(c);
        }
        int number = random.nextInt(10);
        sb.append(number);

        char[] chars = sb.toString().toCharArray();
        int randomIndex = random.nextInt(chars.length);
        char temp = chars[chars.length - 1];
        chars[chars.length - 1] = chars[randomIndex];
        chars[randomIndex] = temp;
        String s = new String(chars);
        return s;
    }
}
class GameJFrame extends JFrame implements KeyListener, ActionListener {//创建一个主界面
    //创建二维数组
    int[][] data = new int[4][4];
    //空白方块的位置
    int x = 0;
    int y = 0;
    //定义一个变量，记录当前图片的路径

    String path="src\\animal\\animal3\\";

    int[][] win = new int[][]{
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    //定义变量用于计步
    int step = 0;
    //创建选项下面的条目对象
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("联系客服");
    JMenuItem Low = new JMenuItem("低等难度");
    JMenuItem medium = new JMenuItem("中等难度");
    JMenuItem high = new JMenuItem("高等难度");
    public GameJFrame() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //初始化数据（打乱）
        initData();
        //初始化图片
        initImage();
        //让界面显示
        this.setVisible(true);
    }
    private void initData() {
        int[] tempArr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();

        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);

            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }

    }
    private void initImage() {
        //清除所有图片
        this.getContentPane().removeAll();
        if (victory()){
            //显示胜利
            JLabel winJLabel = new JLabel(new ImageIcon("src\\win.png"));
            winJLabel.setBounds(203,283,197,73);
            this.getContentPane().add(winJLabel);
        }
        JLabel stepCount = new JLabel("步数："+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = data[i][j];
                //创建一个图片ImageIcon的对象
                //创建一个JLable的对象
                JLabel jLabel = new JLabel(new ImageIcon(path + num + ".jpg"));
                //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //给图片添加边框
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                //将管理容器添加到界面
                this.getContentPane().add(jLabel);

            }
        }
        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("src\\background.png"));
        background.setBounds(40, 40, 508, 560);
        //添加背景图到界面
        this.getContentPane().add(background);

        //刷新界面
        this.getContentPane().repaint();
    }
    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        //创建两个菜单选项的对象
        JMenu functionJmMnu = new JMenu("功能");
        JMenu aboutJmMnu = new JMenu("关于我们");
        JMenu changeImage = new JMenu("更换图片");

        //将美女，动物，运动添加到更换图片中
        changeImage.add(Low);
        changeImage.add(medium);
        changeImage.add(high);

        //将每个条目添加到选项中
        functionJmMnu.add(changeImage);
        functionJmMnu.add(replayItem);
        functionJmMnu.add(reLoginItem);
        functionJmMnu.add(closeItem);
        aboutJmMnu.add(accountItem);

        //将选项添加到菜单中
        jMenuBar.add(functionJmMnu);
        jMenuBar.add(aboutJmMnu);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);

        Low.addActionListener(this);
        medium.addActionListener(this);
        high.addActionListener(this);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        //设置界面的宽高
        this.setSize(603, 680);
        this.setTitle("拼图单机版 v1.0");//设置界面的标题
        this.setAlwaysOnTop(true);//置顶
        this.setLocationRelativeTo(null);//居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置关闭模式

        //取消默认的居中放置
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    //按下不松时调用
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode==65){
            //清除当前页面图片
            this.getContentPane().removeAll();
            //加载完整图片
            JLabel all = new JLabel(new ImageIcon(path+"all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            //添加背景图片
            JLabel background = new JLabel(new ImageIcon("src\\background.png"));
            background.setBounds(40, 40, 508, 560);
            //添加背景图到界面
            this.getContentPane().add(background);

            //刷新界面
            this.getContentPane().repaint();

        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        //胜利后结束方法
        if (victory()){return;}
        int keyCode = e.getKeyCode();
        if (keyCode==37){
            System.out.println("向左移动");
            if (y==3){
                return;
            }
            data[x][y] = data[x][y+1];
            data[x][y+1] = 0;
            y++;

            //计步器
            step++;
            initImage();
        } else if (keyCode==38) {
            System.out.println("向上移动");
            if (x==3){
                return;
            }

            data[x][y] = data[x+1][y];
            data[x+1][y] = 0;
            x++;
            //计步器
            step++;
            initImage();
        }
        else if (keyCode==39) {
            System.out.println("向右移动");
            if (y==0){
                return;
            }
            data[x][y] = data[x][y-1];
            data[x][y-1] = 0;
            y--;
            //计步器
            step++;
            initImage();
        }
        else if (keyCode==40) {
            System.out.println("向下移动");
            if (x==0){
                return;
            }
            data[x][y] = data[x-1][y];
            data[x-1][y] = 0;
            x--;
            //计步器
            step++;
            initImage();
        }
        else if (keyCode==65){
            initImage();
        }
        else if(keyCode==87){
            data = new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();
        }
    }
    //判断胜利
    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        Random r = new Random();
        if (obj == replayItem){
            System.out.println("重新游戏");
            //计步器清零
            step=0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();

        }
        else if (obj == reLoginItem){
            System.out.println("重新登录");
            //关闭当前界面
            this.setVisible(false);
            //返回登录界面
            new LoginJFrame();

        }
        else if (obj == accountItem){
            System.out.println("联系客服");
            //创建一个弹框对象
            JDialog jDialog = new JDialog();
            //创建一个管理图片的容器对象JLabel
            JLabel jLabel = new JLabel(new ImageIcon("src\\about.jpg"));
            //设置位置和宽高
            jLabel.setBounds(0,0,258,258);
            //将图片加入到弹框
            jDialog.getContentPane().add(jLabel);
            //设置弹框大小
            jDialog.setSize(344,344);
            //弹框置顶
            jDialog.setAlwaysOnTop(true);
            //弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框锁定
            jDialog.setModal(true);
            //显示弹框
            jDialog.setVisible(true);

        }
        else if (obj == closeItem){
            System.out.println("关闭游戏");
            //关闭虚拟机即可
            System.exit(0);
        }
        else if (obj == Low){
            System.out.println("低等");
            int index = r.nextInt(8) + 1;
            path = "src\\Low\\animal"+index+"\\";
            initImage();
        }else if (obj == medium){
            System.out.println("中等");
            int index = r.nextInt(13) + 1;
            path = "src\\medium\\girl"+index+"\\";
            initImage();
        }else if (obj == high){
            System.out.println("高等");
            int index = r.nextInt(9) + 1;
            path = "src\\high\\sport"+index+"\\";
            initImage();
        }
    }
}
class LoginJFrame extends JFrame implements MouseListener {//登录界面
    //创建用户
    static ArrayList<User> list = new ArrayList<User>();
    static {
        list.add(new User("龚薪鉴", "25014"));
        list.add(new User("张三", "123"));
        list.add(new User("李四", "1234"));
    }
    //登录
    JButton login = new JButton();
    //添加用户户名输入框
    JTextField username = new JFormattedTextField();
    //密码输入框
    JPasswordField password = new JPasswordField();
    //验证码输入框
    JTextField code = new JFormattedTextField();
    //正确验证码
    String codeStr = CodeUtil.getCode();
    //正确验证码模块
    JLabel rightCode = new JLabel();
    //5.添加注册按钮
    JButton register = new JButton();
    public LoginJFrame() {
        //初始化界面
        initJFrame();
        //在界面添加内容
        initView();
        this.setVisible(true);
    }
    private void initView() {
        //添加用户名文字
        JLabel usernameText = new JLabel(new ImageIcon("src\\login\\用户名.png"));
        usernameText.setBounds(116, 135, 47, 17);
        this.getContentPane().add(usernameText);
        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);
        //添加密码文字
        JLabel passwordText = new JLabel(new ImageIcon("src\\login\\密码.png"));
        passwordText.setBounds(130, 195, 32, 16);
        this.getContentPane().add(passwordText);
        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);
        //验证码提示
        JLabel codeText = new JLabel(new ImageIcon("src\\login\\验证码.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeText);
        code.setBounds(195, 256, 100, 30);
        this.getContentPane().add(code);
        //设置内容
        rightCode.setText(codeStr);
        //设置宽高
        rightCode.setBounds(300, 256, 50, 30);
        //添加到界面
        this.getContentPane().add(rightCode);
        //添加刷新验证码
        rightCode.addMouseListener(this);
        //5.设置登录按钮n
        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon("src\\login\\登录按钮.png"));
        //去除按钮的边框
        login.setBorderPainted(false);
        //去除按钮的背景
        login.setContentAreaFilled(false);
        this.getContentPane().add(login);
        //添加点击事件
        login.addMouseListener(this);
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("src\\login\\注册按钮.png"));
        //去除按钮的边框
        register.setBorderPainted(false);
        //去除按钮的背景
        register.setContentAreaFilled(false);
        this.getContentPane().add(register);
        //添加点击事件
        register.addMouseListener(this);
        //7.添加背景图片
        JLabel background = new JLabel(new ImageIcon("src\\register\\background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);
    }
    private void initJFrame() {
        this.setSize(488, 430);
        this.setTitle("拼图 登录");//设置界面的标题
        this.setAlwaysOnTop(true);//置顶
        this.setLocationRelativeTo(null);//居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置关闭模式
        this.setLayout(null);//取消默认布局
    }
    public void showJDialog(String content) {
        //创建对象
        JDialog jDialog = new JDialog();
        //设置大小
        jDialog.setSize(200, 200);
        //置顶
        jDialog.setAlwaysOnTop(true);
        //居中
        jDialog.setLocationRelativeTo(null);
        //锁定
        jDialog.setModal(true);
        //创建警告文字对象
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 200);
        jDialog.getContentPane().add(warning);
        //让弹框展示
        jDialog.setVisible(true);
    }

    private boolean contains(ArrayList<User> list,User user) {
        for (int i = 0; i < list.size(); i++) {
            User user1 = list.get(i);
            if ((user1.getUsername().equals(user.getUsername())) && (user1.getPassword().equals(user.getPassword()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == login) {
            String usernameText = username.getText();
            String passwordText = password.getText();
            User userInput = new User(usernameText,passwordText);
            String codeText = code.getText();
            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                showJDialog("用户名或密码为空！！！");
            } else {
                if (codeText.equalsIgnoreCase(codeStr)){
                    if (contains(list,userInput)){
                        System.out.println("success");
                        this.setVisible(false);
                        new GameJFrame();
                    }else {
                        showJDialog("用户名不存在！");
                    }
                }else {
                    showJDialog("验证码错误！");

                }
            }
        }else if (obj == rightCode){
            System.out.println("更新验证码");
            codeStr = CodeUtil.getCode();
            //设置内容
            rightCode.setText(codeStr);
        }else if (obj == register){
            showJDialog("<html>当前不支持注册！但提供测试账号：张三，密码：123</html>");
//            new RegisterJFrame();
        }
    }

    //按下不松
    @Override
    public void mousePressed(MouseEvent e) {
        Object click = e.getSource();
        if (click == login) {
            login.setIcon(new ImageIcon("src\\login\\登录按下.png"));
        }else if (click == register){
            register.setIcon(new ImageIcon("src\\login\\注册按下.png"));
        }
    }

    //松开
    @Override
    public void mouseReleased(MouseEvent e) {
        Object click = e.getSource();
        if (click == login) {
            login.setIcon(new ImageIcon("src\\login\\登录按钮.png"));
        }else if (click == register){
            register.setIcon(new ImageIcon("src\\login\\注册按钮.png"));
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
class RegisterJFrame extends JFrame {//注册界面

    public RegisterJFrame(){
        //初始化界面
        initJFrame();

        //
        initView();

        this.setVisible(true);
    }

    private void initView() {

    }

    private void initJFrame() {
        this.setSize(488,500);
        this.setTitle("拼图 登录");//设置界面的标题
        this.setAlwaysOnTop(true);//置顶
        this.setLocationRelativeTo(null);//居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置关闭模式
        this.setLayout(null);//取消默认布局
    }
}
class User {
    private String username;
    private String password;
    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
class App {
    public static void main(String[] args) {
        new LoginJFrame();
    }
}