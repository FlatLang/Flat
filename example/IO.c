#include "IO.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
#include <Fathom.h>

IO* __static__IO;



IO* fathom_IO_IO(ExceptionData* exceptionData)
{
	NEW(IO, reference,);
	
	{
	}
	
	return reference;
}

void fathom_del_IO(IO** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	
	{
	}
	free(*reference);
}

void fathom_IO_println(IO* reference, ExceptionData* exceptionData, String* fathom_text_18)
{
	fathom_IO_print(reference, exceptionData, fathom_text_18);
	fathom_IO_print(reference, exceptionData, fathom_String_String(exceptionData, "\n"));
}

void fathom_IO_print(IO* reference, ExceptionData* exceptionData, String* fathom_text_21)
{
	const char* fathom_cText_21;
	
	fathom_cText_21 = fathom_String_toCharArray(fathom_text_21, exceptionData);
	printf(fathom_cText_21);
}

void fathom_IO_printi(IO* reference, ExceptionData* exceptionData, int fathom_j_24)
{
	printf("%d", fathom_j_24);
}

void fathom_IO_printl(IO* reference, ExceptionData* exceptionData, long_long fathom_j_27)
{
	printf("%llu", fathom_j_27);
}

int fathom_IO_getInt(IO* reference, ExceptionData* exceptionData)
{
	String* fathom_s_30;
	char* fathom_data_30;
	int fathom_num_30;
	
	fathom_s_30 = fathom_IO_getLine(reference, exceptionData);
	fathom_data_30 = fathom_String_toCharArray(fathom_s_30, exceptionData);
	fathom_num_30 = atoi(fathom_data_30);
	return fathom_num_30;
}

String* fathom_IO_getLine(IO* reference, ExceptionData* exceptionData)
{
	char* fathom_line_33;
	String* fathom_s_33;
	
	fathom_line_33 = ufgets(stdin);
	fathom_s_33 = fathom_String_String(exceptionData, fathom_line_33);
	return fathom_s_33;
}

void fathom_IO_waitForEnter(IO* reference, ExceptionData* exceptionData)
{
	char* fathom_c_36;
	
	fathom_c_36 = (char*)malloc(sizeof(char) * 2);
	fgets(fathom_c_36, 2, stdin);
}
