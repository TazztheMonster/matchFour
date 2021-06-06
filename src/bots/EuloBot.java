package bots;

public class EuloBot implements MatchFourBot{
	
	private final String Name = "Eulo Bot";
	private int nextSpalte = 0;
	
	@Override
	public int turn(char[][] board, char icon) {
		
		if(nextSpalte >= 6)
			nextSpalte = 0;
		else nextSpalte++;
		
		return nextSpalte;
	}

	@Override
	public void won(boolean won) {
		nextSpalte = 0;
	}

	@Override
	public String getBotName() {

		return Name;
	}
	
}
