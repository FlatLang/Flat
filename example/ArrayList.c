#include "ArrayList.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <stdlib.h>
#include <Fathom.h>

CCLASS_PRIVATE
(
	int fathom_count;
	int fathom_size;
	int* fathom_data;
)

ArrayList* fathom_ArrayList_ArrayList(ExceptionData* exceptionData)
{
	CCLASS_NEW(ArrayList, this);
	
	this->prv->fathom_count = 0;
	this->prv->fathom_size = 0;
	this->prv->fathom_data = 0;
	{
		this->prv->fathom_size = 0;
		fathom_ArrayList_increaseSize(this, exceptionData);
	}
	
	return this;
}

void fathom_del_ArrayList(ArrayList** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	free((*this)->prv);
	
	{
	}
	free(*this);
}

void fathom_ArrayList_add(ArrayList* this, ExceptionData* exceptionData, int fathom_var_118)
{
	if (this->prv->fathom_count + 1 >= this->prv->fathom_size)
	{
		fathom_ArrayList_increaseSize(this, exceptionData);
	}
	this->prv->fathom_data[this->prv->fathom_count] = fathom_var_118;
	this->prv->fathom_count = this->prv->fathom_count + 1;
}

void fathom_ArrayList_increaseSize(ArrayList* this, ExceptionData* exceptionData)
{
	int* fathom_tmp_125;
	
	this->prv->fathom_size = this->prv->fathom_size + 3;
	fathom_tmp_125 = (int*)malloc(sizeof(int) * (this->prv->fathom_size));
	arrayCopy(fathom_tmp_125, 0, this->prv->fathom_data, 0, this->prv->fathom_count, this->prv->fathom_size, sizeof(int));
	free(this->prv->fathom_data);
	this->prv->fathom_data = fathom_tmp_125;
}

int fathom_ArrayList_getSize(ArrayList* this, ExceptionData* exceptionData)
{
	return this->prv->fathom_count;
}

int fathom_ArrayList_get(ArrayList* this, ExceptionData* exceptionData, int fathom_index_157)
{
	return this->prv->fathom_data[fathom_index_157];
}
