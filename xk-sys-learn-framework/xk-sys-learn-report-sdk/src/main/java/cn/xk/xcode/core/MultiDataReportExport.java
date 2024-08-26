package cn.xk.xcode.core;

import lombok.Builder;

import java.util.concurrent.CountDownLatch;

/**
 * @Author xuk
 * @Date 2024/8/8 10:30
 * @Version 1.0
 * @Description MultiDataReportExport
 */
@Builder
public abstract class MultiDataReportExport implements Runnable {
    private int TotalData;
    private int DataLengthUnit;
    // todo 待完成
    private CountDownLatch countDownLatch;
    @Override
    public void run() {

        handlerDataExport();
    }

    public abstract void handlerDataExport();
}
