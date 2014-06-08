#include "ArrayList.h"
#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
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

void nova_ArrayList_add(ArrayList* this, ExceptionData* exceptionData, int nova_0_var)
{
	if (this->prv->nova_ArrayList_count + 1 >= this->prv->nova_ArrayList_size)
	{
		nova_ArrayList_increaseSize(this, exceptionData);
	}
	this->prv->nova_ArrayList_data[this->prv->nova_ArrayList_count] = nova_0_var;
	this->prv->nova_ArrayList_count = this->prv->nova_ArrayList_count + 1;
}

void nova_ArrayList_increaseSize(ArrayList* this, ExceptionData* exceptionData)
{
	int* nova_100_tmp;
	
	this->prv->nova_ArrayList_size = this->prv->nova_ArrayList_size + 3;
	nova_100_tmp = (int*)malloc(sizeof(int) * (this->prv->nova_ArrayList_size));
	arrayCopy(nova_100_tmp, 0, this->prv->nova_ArrayList_data, 0, this->prv->nova_ArrayList_count, this->prv->nova_ArrayList_size, 4);
	this->prv->nova_ArrayList_data = nova_100_tmp;
}

int nova_ArrayList_getSize(ArrayList* this, ExceptionData* exceptionData)
{
	return this->prv->nova_ArrayList_count;
}

int nova_ArrayList_get(ArrayList* this, ExceptionData* exceptionData, int nova_0_index)
{
	return this->prv->nova_ArrayList_data[nova_0_index];
}
