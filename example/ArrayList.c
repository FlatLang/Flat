#include "ArrayList.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"
#include <stdlib.h>
#include <Fathom.h>

CCLASS_PRIVATE
(
	int nova_ArrayList_count;
	int nova_ArrayList_size;
	int* nova_ArrayList_data;
	
)

ArrayList* nova_ArrayList_ArrayList(ExceptionData* exceptionData)
{
	CCLASS_NEW(ArrayList, this);
	
	this->prv->nova_ArrayList_count = 0;
	this->prv->nova_ArrayList_size = 0;
	this->prv->nova_ArrayList_data = 0;
	{
		this->prv->nova_ArrayList_size = 0;
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

void nova_ArrayList_add(ArrayList* this, ExceptionData* exceptionData, int nova_ArrayList_var_152)
{
	if (this->prv->nova_ArrayList_count + 1 >= this->prv->nova_ArrayList_size)
	{
		nova_ArrayList_increaseSize(this, exceptionData);
	}
	this->prv->nova_ArrayList_data[this->prv->nova_ArrayList_count] = nova_ArrayList_var_152;
	this->prv->nova_ArrayList_count = this->prv->nova_ArrayList_count + 1;
}

void nova_ArrayList_increaseSize(ArrayList* this, ExceptionData* exceptionData)
{
	int* nova_ArrayList_tmp_161;
	
	this->prv->nova_ArrayList_size = this->prv->nova_ArrayList_size + 3;
	nova_ArrayList_tmp_161 = (int*)malloc(sizeof(int) * (this->prv->nova_ArrayList_size));
	arrayCopy(nova_ArrayList_tmp_161, 0, this->prv->nova_ArrayList_data, 0, this->prv->nova_ArrayList_count, this->prv->nova_ArrayList_size, sizeof(int));
	free(this->prv->nova_ArrayList_data);
	this->prv->nova_ArrayList_data = nova_ArrayList_tmp_161;
}

int nova_ArrayList_getSize(ArrayList* this, ExceptionData* exceptionData)
{
	return this->prv->nova_ArrayList_count;
}

int nova_ArrayList_get(ArrayList* this, ExceptionData* exceptionData, int nova_ArrayList_index_172)
{
	return this->prv->nova_ArrayList_data[nova_ArrayList_index_172];
}
