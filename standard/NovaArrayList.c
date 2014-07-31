#include <precompiled.h>
#include "NovaArrayList.h"


nova_VTable_ArrayList nova_VTable_ArrayList_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};
CCLASS_PRIVATE
(
	int nova_ArrayList_bufferSize;
	int* nova_ArrayList_data;
	
)

ArrayList* nova_ArrayList_ArrayList(ArrayList* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(ArrayList, this);
	
	this->nova_ArrayList_size = 0;
	this->prv->nova_ArrayList_bufferSize = 0;
	this->prv->nova_ArrayList_data = 0;
	this->vtable = &nova_VTable_ArrayList_val;
	{
		this->nova_ArrayList_size = 0;
		this->prv->nova_ArrayList_bufferSize = 0;
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
	
	
	
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_ArrayList_add(ArrayList* this, ExceptionData* exceptionData, int nova_0_var)
{
	if (this->nova_ArrayList_size >= this->prv->nova_ArrayList_bufferSize)
	{
		nova_ArrayList_increaseSize(this, exceptionData);
	}
	this->prv->nova_ArrayList_data[this->nova_ArrayList_size++] = nova_0_var;
}

void nova_ArrayList_increaseSize(ArrayList* this, ExceptionData* exceptionData)
{
	int* nova_1_tmp;
	
	this->prv->nova_ArrayList_bufferSize = this->prv->nova_ArrayList_bufferSize + 3;
	nova_1_tmp = (int*)NOVA_MALLOC(sizeof(int) * (this->prv->nova_ArrayList_bufferSize));
	arrayCopy(nova_1_tmp, 0, this->prv->nova_ArrayList_data, 0, this->nova_ArrayList_size, this->prv->nova_ArrayList_bufferSize, 4);
	this->prv->nova_ArrayList_data = nova_1_tmp;
}

int nova_ArrayList_get(ArrayList* this, ExceptionData* exceptionData, int nova_0_index)
{
	return this->prv->nova_ArrayList_data[nova_0_index];
}
