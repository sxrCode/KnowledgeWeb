package com.sxr.knowledge.task;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<WorkerHolder> workerHolders = new LinkedList<>();
		List<WorkerRunable> workerRunables = new LinkedList<>();

		// create
		for (int i = 0; i < 50; i++) {
			WorkerRunable runable = new WorkerRunable();
			workerRunables.add(runable);

			WorkerHolder workerHolder = new WorkerHolder();
			workerHolders.add(workerHolder);
		}

		// start
		for (int i = 0; i < workerRunables.size(); i++) {
			WorkerRunable runable = workerRunables.get(i);
			WorkerHolder holder = workerHolders.get(i);
			Thread thread = new Thread(runable);
			Date currentDate = new Date();
			holder.setStartTime(currentDate);
			holder.setLastCheckTime(currentDate);
			holder.setRunableName(thread.getName());
			thread.start();
		}

		Runnable monitor = new Runnable() {

			@Override
			public void run() {
				boolean finish = false;
				while (!finish) {
					finish = true;
					for (int i = 0; i < workerRunables.size(); i++) {
						WorkerRunable runable = workerRunables.get(i);
						WorkerHolder holder = workerHolders.get(i);
						if (!holder.isEnable()) {
							continue;
						}
						finish = false;

						if (runable.getStatus() == 1) {
							Date currentDate = new Date();
							int proceedSecond = (int) ((currentDate.getTime() - holder.getStartTime().getTime()) / 1000)
									- 9;
							if (proceedSecond > 0) {
								int checkIntervalSecond = (int) ((currentDate.getTime()
										- holder.getLastCheckTime().getTime()) / 1000);
								if (checkIntervalSecond % 3 == 2) {
									System.out.println(holder.getRunableName() + " 已超时" + proceedSecond + "秒，请注意！");
									holder.setLastCheckTime(currentDate);
								}
							}
						} else if (runable.getStatus() == 2) {
							System.out.println(holder.getRunableName() + " 执行成功");
							holder.setEnable(false);

						} else if (runable.getStatus() == 3) {
							System.out.println(holder.getRunableName() + " 执行失败");
							holder.setEnable(false);
						}
					}
				}

			}
		};

		Thread monitorThread = new Thread(monitor);
		monitorThread.run();
	}

}

class WorkerHolder {

	private Date startTime;
	private Date lastCheckTime;
	private String runableName;
	private boolean enable = true;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getLastCheckTime() {
		return lastCheckTime;
	}

	public void setLastCheckTime(Date lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
	}

	public String getRunableName() {
		return runableName;
	}

	public void setRunableName(String runableName) {
		this.runableName = runableName;
	}

}

class WorkerRunable implements Runnable {

	private int status = 0; // 0-尚未执行 1-执行中 2-成功 3-失败

	@Override
	public void run() {

		try {
			status = 1;
			long executeTime = (long) (Math.random() * 25000);
			Thread.sleep(executeTime);
			status = 2;
		} catch (InterruptedException e) {
			e.printStackTrace();
			status = 3;
		}
	}

	public int getStatus() {
		return status;
	}

}
