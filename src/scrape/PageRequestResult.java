package scrape;

public class PageRequestResult {

	private int finalCount;
	private int currentPage;
	
	PageRequestResult(int currentPage, int finalCount) {
		this.currentPage = currentPage;
		this.finalCount = finalCount;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public int getFinalCount() {
		return finalCount;
	}
}
