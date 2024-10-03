package loginUser;

public class FolhaPagto {
	private int     referFolha;
	private int     idFunc;
	private double  vlrBruto;
	private double  vlrInss;
	private double  vlrLiquido;
	
	public double calcularFolha(int pReferFolha, int pIdFunc, int pQtdHoras, double pSalaHora) {
		double vlrBrutoCalculado = pQtdHoras * pSalaHora;
		
		return vlrBrutoCalculado;
	}
	
	// area de getters e setters
	public int getReferFolha() {
		return referFolha;
	}
	public void setReferFolha(int referFolha) {
		this.referFolha = referFolha;
	}
	public int getIdFunc() {
		return idFunc;
	}
	public void setIdFunc(int idFunc) {
		this.idFunc = idFunc;
	}
	public double getVlrBruto() {
		return vlrBruto;
	}
	public void setVlrBruto(double vlrBruto) {
		this.vlrBruto = vlrBruto;
	}
	public double getVlrInss() {
		return vlrInss;
	}
	public void setVlrInss(double vlrInss) {
		this.vlrInss = vlrInss;
	}
	public double getVlrLiquido() {
		return vlrLiquido;
	}
	public void setVlrLiquido(double vlrLiquido) {
		this.vlrLiquido = vlrLiquido;
	}
	
	
	
}
