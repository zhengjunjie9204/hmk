package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xgx.syzj.R;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;
import com.yunnex.printlib.PrintUtils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 收银记账结果页
 *
 * @author zajo
 * @created 2015年09月23日 10:33
 */
public class RevenueResultActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_money;
    private Member member;
    private static final int REQUEST_PRINT_CODE = 0x7000001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revenue_result);
        setTitleText("");
        setSubmit(getString(R.string.revenue_result_button_continue));
        tv_money = (TextView) findViewById(R.id.tv_money);
        findViewById(R.id.btn_continue).setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String money = bundle.getString("money");
        if (!TextUtils.isEmpty(money)){
            tv_money.setText("¥ "+money);
        }
        member = bundle.getParcelable("member");
    }

    @Override
    protected void submit() {
        super.submit();
        gotoActivity(RevenueFastActivity.class);
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent1;
        switch (v.getId()){
            case R.id.btn_continue:
//                gotoActivity(RevenueActivity.class);
////                AppManager.getAppManager().returnToActivity(RevenueActivity.class);
////                ((RevenueActivity)(AppManager.getAppManager().currentActivity())).clean();
                 intent1 = PrintUtils.bindIntent(getPrinterQueue());
                if (intent1 != null)
                {
                    try
                    {
                        startActivityForResult(intent1, REQUEST_PRINT_CODE);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(RevenueResultActivity.this, "掌贝打印SDK未安装", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(RevenueResultActivity.this, "error: data is null", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_send:
                Intent intent = new Intent(this, SaleHistoryActivity.class);
//                gotoActivity(SaleHistoryActivity.class);
                intent.setFlags(3000);
                startActivity(intent);
                defaultFinish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_PRINT_CODE:
                switch (resultCode)
                {
                    case RESULT_OK:
                        Toast.makeText(this, "打印成功", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(this, "打印失败", Toast.LENGTH_LONG).show();
                        break;
                }
                break;

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().returnToActivity(MainActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void goBack(View view) {
        super.goBack(view);
        AppManager.getAppManager().returnToActivity(MainActivity.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    // 组装打印数据
    private Queue<PrintUtils.PrintData> getPrinterQueue()
    {
        Queue<PrintUtils.PrintData> printer = new LinkedList<PrintUtils.PrintData>();
        try
        {
            PrintUtils.PrintData qrCodePrinter;
            StringBuffer sb = new StringBuffer();
            // 居中打印
            sb.append(PrintUtils.printCenter("我是标题", PrintUtils.LINE_2));
            // 打印分割实线
            sb.append(PrintUtils.printLine(PrintUtils.LINE_2));
            // 普通打印
            sb.append(PrintUtils.print("2016年8月19日"));
            // 获取字节流
            qrCodePrinter = PrintUtils.getByte(sb.toString());
            // 加入打印列表
            printer.add(qrCodePrinter);

            sb = new StringBuffer();
            sb.append(PrintUtils.print("高宽加倍字"));
            int textline = PrintUtils.getLineForTextSize(PrintUtils.TEXTFONT_BIG);
            sb.append(PrintUtils.printCenter("高宽加倍字居中", textline));
            qrCodePrinter = PrintUtils.getByte(sb.toString(), 2);
            printer.add(qrCodePrinter);
            sb = new StringBuffer();
            sb.append(PrintUtils.print("我是二倍高字体"));
            qrCodePrinter = PrintUtils.getByte(sb.toString(), 3);
            printer.add(qrCodePrinter);
            sb = new StringBuffer();
            sb.append(PrintUtils.print("我是二倍宽字体"));
            qrCodePrinter = PrintUtils.getByte(sb.toString(), 4);
            printer.add(qrCodePrinter);
            sb = new StringBuffer();
            sb.append(PrintUtils.print("我是特大号字"));
            qrCodePrinter = PrintUtils.getByte(sb.toString(), 5);
            printer.add(qrCodePrinter);

            sb = new StringBuffer();
            // 打印虚线
            sb.append(PrintUtils.printDottedLine(PrintUtils.LINE_2));
            sb.append(PrintUtils.print("能给商户带来利益的应用"));
            // 2边对齐打印
            sb.append(PrintUtils.printSide("剁椒鱼头", "2份", PrintUtils.LINE_2));
            for (int i = 0; i < 2; i++)
            {
                // 多列对齐打印
                sb.append(PrintUtils.printColumns(20, "剁椒鱼头"));
                sb.append(PrintUtils.printColumns(PrintUtils.LINE_2 - 20, "2份")).append("\n");
                sb.append(PrintUtils.printColumns(20, "凉拌三丝（大份）"));
                sb.append(PrintUtils.printColumns(PrintUtils.LINE_2 - 20, "3份")).append("\n");
            }
            sb.append("\n\n\n");
            qrCodePrinter = PrintUtils.getByte(sb.toString());
            printer.add(qrCodePrinter);

            //自定义排版
            sb = new StringBuffer();
            sb.append("让天下没有难做的掌柜\n");
            sb.append("  让天下没有难做的掌柜\n");
            sb.append("    让天下没有难做的掌柜\n");
            sb.append("      让天下没有难做的掌柜\n");
            qrCodePrinter = PrintUtils.getByte(sb.toString());
            printer.add(qrCodePrinter);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return printer;
    }
}