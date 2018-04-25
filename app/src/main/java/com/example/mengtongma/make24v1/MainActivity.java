package com.example.mengtongma.make24v1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btnAdd;
    private Button btnSubtract;
    private Button btnMultiple;
    private Button btnDivide;
    private Button btnLeftBracket;
    private Button btnRightBracket;
    private Button btnBack;
    private Button btnSubmit;
    private Chronometer timeConsume;
    private TextView attemptTimes;
    private TextView successTimes;
    private TextView skippedTime;
    private Button btnNext;
    private TextView answerInput;
    private NumberPicker numberPicker1;
    private NumberPicker numberPicker2;
    private NumberPicker numberPicker3;
    private NumberPicker numberPicker4;


    private int num1;
    private int num2;
    private int num3;
    private int num4;
    private int submitTimes = 1;
    private int skipTimes = 0;
    private int rightAnswer = 0;
    private static final int N = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btn1 = findViewById(R.id.btnNumber1);
        btn2 = findViewById(R.id.btnNumber2);
        btn3 = findViewById(R.id.btnNumber3);
        btn4 = findViewById(R.id.btnNumber4);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiple = findViewById(R.id.btnMultiple);
        btnDivide = findViewById(R.id.btnDivide);
        btnLeftBracket = findViewById(R.id.btnLeftBracket);
        btnRightBracket = findViewById(R.id.btnRightBracket);
        btnBack = findViewById(R.id.btnDelete);
        btnSubmit = findViewById(R.id.btnSubmit);
        //btnNext = findViewById(R.id.btnSkip);
        timeConsume = findViewById(R.id.elapsedTime);
        answerInput = findViewById(R.id.input);
        attemptTimes = findViewById(R.id.attempt);
        successTimes = findViewById(R.id.rightTimes);
        skippedTime = findViewById(R.id.skippedTimes);

        genRandomNumber();

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);
        btnMultiple.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnLeftBracket.setOnClickListener(this);
        btnRightBracket.setOnClickListener(this);
        //btnNext.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        timeConsume.start();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.btnClear:
                answerInput.setText(null);
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
                btn4.setEnabled(true);
                btnSubmit.setEnabled(false);
                return true;
            case R.id.btnForward:
                //Toast.makeText(this, "skip", Toast.LENGTH_SHORT).show();
                submitTimes = 1;
                attemptTimes.setText(Integer.toString(submitTimes));
                skipTimes++;
                skippedTime.setText(Integer.toString(skipTimes));
                answerInput.setText(null);
                genRandomNumber();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.show_solution) {
            // Show the solution
            solve(num1, num2, num3, num4);
            genRandomNumber();
            skipTimes++;
            skippedTime.setText(Integer.toString(skipTimes));

        } else if (id == R.id.assign_number) {
            assignNumber();
            skipTimes++;
            skippedTime.setText(Integer.toString(skipTimes));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void assignNumber() {


        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setTitle("Assign Numbers");
        builder.setIcon(R.mipmap.assign);
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.number_picker, null);
        numberPicker1 = view.findViewById(R.id.noPicker1);
        numberPicker2 = view.findViewById(R.id.noPicker2);
        numberPicker3 = view.findViewById(R.id.noPicker3);
        numberPicker4 = view.findViewById(R.id.noPicker4);

        //set number picker range
        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(9);
        numberPicker2.setMinValue(1);
        numberPicker2.setMaxValue(9);
        numberPicker3.setMinValue(1);
        numberPicker3.setMaxValue(9);
        numberPicker4.setMinValue(1);
        numberPicker4.setMaxValue(9);

        num1 = 1;
        num2 = 1;
        num3 = 1;
        num4 = 1;


        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    num1 = i1;
            }
        });

        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                num2 = i1;
            }
        });

        numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                num3 = i1;
            }
        });

        numberPicker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                num4 = i1;
            }
        });
        builder.setView(view);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setNumber();;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }

    private void solve(int a, int b, int c, int d) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setIcon(R.drawable.show_answer);
        dialog.setTitle("Solution");
        String result = getSolution(a, b, c, d);
        if (null != result) {
            dialog.setMessage(result);

        } else {
            dialog.setMessage("Sorry, there are actually no solutions.");
        }

        dialog.show();

        return;
    }

    private String getSolution(int a, int b, int c, int d) {
        int[] n = { a, b, c, d };
        char[] o = { '+', '-', '*', '/' };
        for (int w = 0; w < 4; w++) {
            for (int x = 0; x < 4; x++) {
                if (x == w)
                    continue;
                for (int y = 0; y < 4; y++) {
                    if (y == x || y == w)
                        continue;
                    for (int z = 0; z < 4; z++) {
                        if (z == w || z == x || z == y)
                            continue;
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 4; j++) {
                                for (int k = 0; k < 4; k++) {
                                    String result = eval(n[w], n[x], n[y], n[z], o[i], o[j], o[k]);
                                    if (null != result) {
                                        return result;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private String eval(int a, int b, int c, int d, char x, char y, char z) {
        try {
            if (bingo(eval(eval(eval(a, x, b), y, c), z, d))) {
                return "((" + a + x + b + ")" + y + c + ")" + z + d;
            }
            if (bingo(eval(eval(a, x, eval(b, y, c)), z, d))) {
                return "(" + a + x + "(" + b + y + c + "))" + z + d;
            }
            if (bingo(eval(a, x, eval(eval(b, y, c), z, d)))) {
                return "" + a + x + "((" + b + y + c + ")" + z + d + ")";
            }
            if (bingo(eval(a, x, eval(b, y, eval(c, z, d))))) {
                return "" + a + x + "(" + b + y + "(" + c + z + d + ")" + ")";
            }
            if (bingo(eval(eval(a, x, b), y, eval(c, z, d)))) {
                return "((" + a + x + b + ")" + y + "(" + c + z + d + "))";
            }
        } catch (Throwable t) {
        }
        return null;
    }
    private static boolean bingo(double x) {
        return Math.abs(x - N) < 0.0000001;
    }

    private static double eval(double a, char x, double b) {
        switch (x) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            default:
                return a / b;
        }
    }

    private void genRandomNumber() {
        Random rand = new Random();

        while(true) {
            num1 = rand.nextInt(8) + 1;
            num2 = rand.nextInt(8) + 1;
            num3 = rand.nextInt(8) + 1;
            num4 = rand.nextInt(8) + 1;

            if(getSolution(num1, num2, num3, num4) != null){
                break;
            }
        }


        setNumber();

    }

    private void setNumber() {

        String btn1Text = Integer.toString(num1);
        String btn2Text = Integer.toString(num2);
        String btn3Text = Integer.toString(num3);
        String btn4Text = Integer.toString(num4);


        btn1.setText(btn1Text);
        btn2.setText(btn2Text);
        btn3.setText(btn3Text);
        btn4.setText(btn4Text);

        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        timeConsume.setBase(SystemClock.elapsedRealtime());
        answerInput.setText(null);
        btnSubmit.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        String cur = answerInput.getText().toString();

        switch (view.getId()) {
            case R.id.btnNumber1:
                String inputNum1 = Integer.toString(num1);
                if(cur.length() != 0 && Character.isDigit(cur.charAt(cur.length() - 1))){
                    Toast.makeText(getApplicationContext(), "Invalid!", Toast.LENGTH_SHORT).show();
                }else
                {
                    answerInput.setText(answerInput.getText() + inputNum1);
                    btn1.setEnabled(false);
                }
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnNumber2:
                String inputNum2 = Integer.toString(num2);
                if(cur.length() != 0 && Character.isDigit(cur.charAt(cur.length() - 1))){
                    Toast.makeText(getApplicationContext(), "Invalid!", Toast.LENGTH_SHORT).show();
                }else{
                    answerInput.setText(answerInput.getText() + inputNum2);
                    btn2.setEnabled(false);
                }
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnNumber3:
                String inputNum3 = Integer.toString(num3);
                if(cur.length() != 0 && Character.isDigit(cur.charAt(cur.length() - 1))){
                    Toast.makeText(getApplicationContext(), "Invalid!", Toast.LENGTH_SHORT).show();
                }else{
                    answerInput.setText(answerInput.getText() + inputNum3);
                    btn3.setEnabled(false);
                }
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnNumber4:
                String inputNum4 = Integer.toString(num4);
                if(cur.length() != 0 && Character.isDigit(cur.charAt(cur.length() - 1))){
                    Toast.makeText(getApplicationContext(), "Invalid!", Toast.LENGTH_SHORT).show();
                }else{
                    answerInput.setText(answerInput.getText() + inputNum4);
                    btn4.setEnabled(false);
                }
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnAdd:
                answerInput.setText(answerInput.getText() + "+");
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnSubtract:
                answerInput.setText(answerInput.getText() + "-");
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnMultiple:
                answerInput.setText(answerInput.getText() + "*");
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnDivide:
                answerInput.setText(answerInput.getText() + "/");
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnLeftBracket:
                answerInput.setText(answerInput.getText() + "(");
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnRightBracket:
                answerInput.setText(answerInput.getText() + ")");
                btnSubmit.setEnabled(true);
                break;
            case R.id.btnDelete:
                if(answerInput.getText().toString().length() != 0) {
                    deleteFunc();
                }
                break;
            case R.id.btnSubmit:
                submitTimes++;
                attemptTimes.setText(Integer.toString(submitTimes));
                // check if all four numbers used
                btnSubmit.setEnabled(false);
                if(btn1.isEnabled() || btn2.isEnabled() || btn3.isEnabled() || btn4.isEnabled()) {

                    Snackbar.make(view, "Incorrect. Please try again!", Snackbar.LENGTH_LONG)
                            .setAction("Action",  null)
                            .show();

                    //wrongAnswer++;
                    //skippedTime.setText(Integer.toString(wrongAnswer));
                    break;
                }
                boolean isRight = calculateResult();

                if(isRight) {
                    showDialog();
                }else {
                    // show message
                    Snackbar.make(view, "Incorrect. Please try again!", Snackbar.LENGTH_LONG)
                            .setAction("Action",  null)
                            .show();
                }
                break;
        }

    }
    private void showDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setIcon(R.drawable.succeed);
        dialog.setTitle("Succeed!");
        String message = "Bingo! ";
        message += answerInput.getText().toString();
        message += "=24";
        dialog.setMessage(message);
        dialog.setPositiveButton("Next Puzzle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                rightAnswer++;
                successTimes.setText(Integer.toString(rightAnswer));
                answerInput.setText(null);

                genRandomNumber();
                submitTimes = 1;
                attemptTimes.setText(Integer.toString(submitTimes));
            }
        });

        dialog.show();
    }

    private void deleteFunc() {
        String curInput = answerInput.getText().toString();
        int length = curInput.length();
        if(length == 1) {
            btnSubmit.setEnabled(false);
        }
        char lastChar = curInput.charAt(length - 1);
        curInput = curInput.substring(0, length - 1);
        answerInput.setText(curInput);
        if(Character.isDigit(lastChar)) {
            int curNum = lastChar - '0';
            if(!btn1.isEnabled() && curNum == num1) {
                btn1.setEnabled(true);
            }
            else if(!btn2.isEnabled() && curNum == num2) {
                btn2.setEnabled(true);
            }
            else if(!btn3.isEnabled() && curNum == num3) {
                btn3.setEnabled(true);
            }
            else if(!btn4.isEnabled() && curNum == num4) {
                btn4.setEnabled(true);
            }
        }
    }

    private boolean calculateResult() {
        String userInput = answerInput.getText().toString();
        String postExpression = convertToPostFix(userInput);
        int result = calculate(postExpression);
        return result == 24;

    }

    private String convertToPostFix(String input) {
        StringBuilder sb = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if(Character.isDigit(ch)) {
                sb.append(ch);
            }
            //left bracket
            if (ch == '(') {
                operatorStack.push(ch);
            }
            //operator
            if(isOperator(ch)) {
                if(operatorStack.isEmpty()) {
                    operatorStack.push(ch);
                }
                else{
                    char stackTop = operatorStack.peek();
                    if(priority(ch) > priority(stackTop)) {
                        operatorStack.push(ch);
                    }else {
                        stackTop = operatorStack.pop();
                        sb.append(stackTop);
                        operatorStack.push(ch);
                    }
                }

            }
            //right bracket
            if(ch == ')') {
                char top = operatorStack.pop();
                while(top != '(') {
                    sb.append(top);
                    top = operatorStack.pop();
                }
            }
        }
        while(!operatorStack.isEmpty()) {
            sb.append(operatorStack.pop());
        }

        return sb.toString();

    }
    private int priority(char ch) {
        if(ch == '+' || ch == '-'){
            return 1;
        }
        if(ch == '*' || ch == '/') {
            return 2;
        }
        return 0;
    }

    private boolean isOperator(char ch) {
        return (ch == '+') || (ch == '-') || (ch == '*') || (ch == '/');
    }

    private int calculate(String input) {
        Stack<Integer> stack = new Stack<>();
        int d1 = 0, d2 = 0, d3 = 0;
        for(int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if(Character.isDigit(ch)) {
                stack.push(ch - '0');
            }
            else {
                if(!stack.isEmpty()) {
                    d2 = stack.pop();
                }
                if(!stack.isEmpty()) {
                    d1 = stack.pop();
                }
                switch (ch){
                    case '+':
                        d3 = d1 + d2;
                        break;
                    case '-':
                        d3 = d1 - d2;
                        break;
                    case '*':
                        d3 = d1 * d2;
                        break;
                    case '/':
                        d3 = d1 / d2;
                        break;

                }
                stack.push(d3);
            }
        }
        return stack.pop();
    }
}
