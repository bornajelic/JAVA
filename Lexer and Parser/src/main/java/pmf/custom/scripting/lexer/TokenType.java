package pmf.custom.scripting.lexer;

public enum TokenType {

	EOF, // kraj teksta

	WORD, // Ovo je neki string

	OPEN_TAG, // {$

	CLOSE_TAG, // $}

	VARIABLE,

	OPERATOR, // +-*/

	FUNCTION, // @func

	EL_STRING, // "neki tekst u navodnicima"

	DOUBLE,

	INT,
	
	FOR,
	
	ECHO,
	
	END,
	
}
