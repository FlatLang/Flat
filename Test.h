#ifndef FILE_Test_FATHOM
#define FILE_Test_FATHOM

#include "CClass.h"
#include "IO.h"
#include "String.h"

CLASS
(
	Test, 
	
	String publicVariable;
	
	FUNC(void, main, Test* __o__, String args[]);
	FUNC(int, test, Test* __o__);
	FUNC(int, test2, Test* __o__);
)

#endif