package cn.jacky.bundle_main.ui.activity;

import android.app.Activity;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.table.TableData;
import com.zenchn.apilib.util.JavaKit;
import com.zenchn.support.router.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.bean.TableInspectEntity;
import cn.jacky.bundle_main.bean.TableScoreEntity;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;

/**
 * 表格demo
 *
 * @author HZJ
 */
public class TableActivity extends BaseActivity {

    @BindView(R2.id.table)
    SmartTable mTable;
    @BindView(R2.id.table_score)
    SmartTable mTableScore;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_table;
    }

    @Override
    public void initWidget() {
        //是否开启表格缩放
        mTable.setZoom(false);
        //是否显示x,y方向的序列号
        mTable.getConfig()
                .setShowXSequence(false)
                .setShowYSequence(false);

        //1注解方式 填充表格数据
        List<TableInspectEntity> list = new ArrayList<>();
        int size = 15;
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            TableInspectEntity entity = new TableInspectEntity();
            entity.setDate(JavaKit.Date.getYmdhms(System.currentTimeMillis()));
            entity.setNum1((double) random.nextInt(20));
            entity.setNum2((double) random.nextInt(34));
            entity.setNum3((double) random.nextInt(49));
            list.add(entity);
        }
        mTable.setData(list);

        //2代码方式
        //普通列
        List<Column> columnList = new ArrayList<>();
        Column<String> column1 = new Column<>("姓名", "name");
        Column<Double> column2 = new Column<>("语文", "mandarin");
        Column<Double> column3 = new Column<>("数学", "math");
        Column<Double> column4 = new Column<>("英语", "english");
        columnList.add(column1);
        columnList.add(column2);
        columnList.add(column3);
        columnList.add(column4);

        //填充数据
        List<TableScoreEntity> scoreList = new ArrayList<>();
        int scoreSize = 15;
        Random random2 = new Random();
        for (int i = 0; i < scoreSize; i++) {
            TableScoreEntity entity = new TableScoreEntity();
            entity.setName(String.format(Locale.CHINA, "学生%1$d", i));
            entity.setMandarin(random2.nextInt(100));
            entity.setMath(random2.nextInt(100));
            entity.setEnglish(random2.nextInt(100));
            scoreList.add(entity);
        }

        TableData<TableScoreEntity> tableData = new TableData<>("期末考试成绩单", scoreList, columnList);
        mTableScore.setTableData(tableData);
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    public static void launch(Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(TableActivity.class)
                .launch();
    }
}
