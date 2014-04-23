#include "ExceptionData.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <setjmp.h>
#include "ArrayList.h"

PRIVATE
(
	jmp_buf* fathom_buffer;
	ExceptionData* fathom_parent;
	ArrayList* fathom_codes;
)

ExceptionData* fathom_ExceptionData_ExceptionData(ExceptionData* exceptionData, jmp_buf* fathom_buf_61)
{
	NEW(ExceptionData, reference);
	
	reference->prv->fathom_buffer = 0;
	reference->prv->fathom_parent = 0;
	reference->prv->fathom_codes = 0;
	{
		reference->prv->fathom_buffer = fathom_buf_61;
		reference->prv->fathom_codes = fathom_ArrayList_ArrayList(exceptionData);
	}
	
	return reference;
}

void fathom_del_ExceptionData(ExceptionData** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	
	fathom_del_ExceptionData(&(*reference)->prv->fathom_parent, exceptionData);
	fathom_del_ArrayList(&(*reference)->prv->fathom_codes, exceptionData);
	free((*reference)->prv);
	
	{
	}
	free(*reference);
}

ArrayList* fathom_ExceptionData_getCodes(ExceptionData* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_codes;
}

void fathom_ExceptionData_addCode(ExceptionData* reference, ExceptionData* exceptionData, int fathom_code_67)
{
	fathom_ArrayList_add(reference->prv->fathom_codes, exceptionData, fathom_code_67);
}

jmp_buf* fathom_ExceptionData_getBuffer(ExceptionData* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_buffer;
}

ExceptionData* fathom_ExceptionData_getCorrectData(ExceptionData* reference, ExceptionData* exceptionData, int fathom_code_73)
{
	ExceptionData* fathom_data_73;
	
	fathom_data_73 = reference;
	while (fathom_data_73 != 0)
	{
		ArrayList* fathom_list_175;
		int fathom_i_175;
		
		fathom_list_175 = fathom_ExceptionData_getCodes(fathom_data_73, exceptionData);
		for (fathom_i_175 = 0; fathom_i_175 < fathom_ArrayList_getSize(fathom_list_175, exceptionData); fathom_i_175++)
		{
			if (fathom_ArrayList_get(fathom_list_175, exceptionData, fathom_i_175) == fathom_code_73)
			{
				return fathom_data_73;
			}
		}
		if (fathom_ExceptionData_getParent(fathom_data_73, exceptionData) == 0)
		{
			return fathom_data_73;
		}
		fathom_data_73 = fathom_ExceptionData_getParent(fathom_data_73, exceptionData);
	}
	return 0;
}

jmp_buf* fathom_ExceptionData_getCorrectBuffer(ExceptionData* reference, ExceptionData* exceptionData, int fathom_code_76)
{
	ExceptionData* fathom_data_76;
	
	fathom_data_76 = fathom_ExceptionData_getCorrectData(reference, exceptionData, fathom_code_76);
	if (fathom_data_76 == 0)
	{
		return 0;
	}
	return fathom_ExceptionData_getBuffer(fathom_data_76, exceptionData);
}

void fathom_ExceptionData_jumpToBuffer(ExceptionData* reference, ExceptionData* exceptionData, int fathom_code_79)
{
	ExceptionData* fathom_data_79;
	jmp_buf* fathom_buf_79;
	
	fathom_data_79 = fathom_ExceptionData_getCorrectData(reference, exceptionData, fathom_code_79);
	if (fathom_ExceptionData_getParent(fathom_data_79, exceptionData) == 0)
	{
		fathom_code_79 = 1;
	}
	fathom_buf_79 = fathom_ExceptionData_getBuffer(fathom_data_79, exceptionData);
	longjmp(*fathom_buf_79, fathom_code_79);
}

ExceptionData* fathom_ExceptionData_getParent(ExceptionData* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_parent;
}

void fathom_ExceptionData_setParent(ExceptionData* reference, ExceptionData* exceptionData, ExceptionData* fathom_p_85)
{
	reference->prv->fathom_parent = fathom_p_85;
}
