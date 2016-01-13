package com.crawler.tentacle.html.getter.weibo;

import java.util.LinkedList;
import java.util.Queue;

public class WeiboCookieManager extends Thread {

	private final String WEIBO_ACCOUNT_FILE = ".//configs//accounts.txt";

	private volatile static WeiboCookieManager mInstance = null;
	private boolean mRun;
	private int SLEEP_MIL = 500;

	private Queue<WeiboUser> mUsers;

	private WeiboCookieManager() {
		System.out.println("[System]:WeiboCookieManager startup...");
		mRun = true;
		mUsers = new LinkedList<WeiboUser>();
		this.start();
	}

	public static WeiboCookieManager getInstance() {
		if (mInstance == null) {
			synchronized (WeiboCookieManager.class) {
				if (mInstance == null) {
					mInstance = new WeiboCookieManager();
				}
			}
		}
		return mInstance;
	}

	@Override
	public synchronized void start() {
		super.start();
		loadAccounts();
	}

	private void loadAccounts() {
		FileLoadTool file = new FileLoadTool();
		String[] fromFile = file.loadStringFromFile(WEIBO_ACCOUNT_FILE).split("\n");
		for (String acNpw : fromFile) {
			String[] info = acNpw.split(",");
			System.out.println(acNpw);
			mUsers.add(new WeiboUser(info[0], info[1]));
		}
	}

	@Override
	public void run() {
		while (mRun) {
			try {
				WeiboCookieManager.sleep(SLEEP_MIL);
				updateAllUserState(SLEEP_MIL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateAllUserState(int d) {
		for (WeiboUser weiboUser : mUsers) {
			weiboUser.update(d);
		}
	}

	public String getCookieString() {
		WeiboUser _first = mUsers.remove();
		if (!_first.ready()) {
			try {
				Thread.sleep(_first.getWaitTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String ret = _first.getCookieAndSink();
		mUsers.add(_first);

		return ret;
	}
}
