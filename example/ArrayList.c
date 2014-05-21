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
	int nova_count;
	int nova_size;
	int* nova_data;
)

ArrayList* nova_ArrayList_ArrayList(ExceptionData* exceptionData)
{
	CCLASS_NEW(ArrayList, this);
	
	this->prv->nova_count = 0;
	this->prv->nova_size = 0;
	this->prv->nova_data = 0;
	{
		this->prv->nova_size = 0;
		nova_ArrayList_increaseSize(this, exceptionData);
	}
	
	return this;
}

void nova_del_ArrayList(ArrayList** this, ExceptionData* exceptionData)
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

void nova_ArrayList_add(ArrayList* this, ExceptionData* exceptionData, int nova_var_159)
{
	if (this->prv->nova_count + 1 >= this->prv->nova_size)
	{
		nova_ArrayList_increaseSize(this, exceptionData);
	}
	nova_data[this->prv->nova_count] = nova_var_159;
	this->prv->nova_count = this->prv->nova_count + 1;
}

void nova_ArrayList_increaseSize(ArrayList* this, ExceptionData* exceptionData)
{
	int* nova_tmp_162;
	
	this->prv->nova_size = this->prv->nova_size + 3;
	nova_tmp_162 = (int*)malloc(sizeof(int) * (nova_size));
	arrayCopy(nova_tmp_162, 0, this->prv->nova_data, 0, this->prv->nova_count, this->prv->nova_size, sizeof(int));
	free(this->prv->nova_data);
	this->prv->nova_data = nova_tmp_162;
}

int nova_ArrayList_getSize(ArrayList* this, ExceptionData* exceptionData)
{
	return this->prv->nova_count;
}

int nova_ArrayList_get(ArrayList* this, ExceptionData* exceptionData, int nova_index_168)
{
	return nova_data[nova_index_168];
}
