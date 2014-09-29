#include <precompiled.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>


nova_VTable_nova_standard_exception_NovaExceptionData nova_VTable_nova_standard_exception_NovaExceptionData_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	buffer* nova_standard_exception_NovaExceptionData_Novabuf;
	nova_standard_exception_NovaExceptionData* nova_standard_exception_NovaExceptionData_Novaparent;
	
)

nova_standard_exception_NovaExceptionData* nova_standard_exception_NovaExceptionData_Novaconstruct(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData, buffer* l0_Novabuf)
{
	CCLASS_NEW(nova_standard_exception_NovaExceptionData, this);
	this->vtable = &nova_VTable_nova_standard_exception_NovaExceptionData_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaExceptionData_Novasuper(this, 0);
	
	{
		nova_standard_exception_NovaExceptionData_Novathis(this, exceptionData, l0_Novabuf);
	}
	
	return this;
}

void nova_del_ExceptionData(nova_standard_exception_NovaExceptionData** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_ExceptionData(&(*this)->prv->nova_standard_exception_NovaExceptionData_Novaparent, exceptionData);
	NOVA_FREE((*this)->prv);
	nova_del_Exception(&(*this)->nova_standard_exception_NovaExceptionData_NovathrownException, exceptionData);
	nova_del_ArrayList(&(*this)->nova_standard_exception_NovaExceptionData_Novacodes, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_exception_NovaExceptionData_Novathis(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData, buffer* l0_Novabuf)
{
	this->prv->nova_standard_exception_NovaExceptionData_Novabuf = l0_Novabuf;
	this->nova_standard_exception_NovaExceptionData_Novacodes = nova_standard_datastruct_NovaArrayList_Nova0_construct(0, exceptionData);
}

void nova_standard_exception_NovaExceptionData_NovaaddCode(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode)
{
	nova_standard_datastruct_NovaArrayList_Novaadd(this->nova_standard_exception_NovaExceptionData_Novacodes, exceptionData, (nova_standard_NovaObject*)(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, l0_Novacode)));
}

buffer* nova_standard_exception_NovaExceptionData_NovagetBuffer(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->prv->nova_standard_exception_NovaExceptionData_Novabuf;
}

nova_standard_exception_NovaExceptionData* nova_standard_exception_NovaExceptionData_NovagetCorrectData(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode)
{
	nova_standard_exception_NovaExceptionData* l1_Novadata;
	
	l1_Novadata = this;
	while (l1_Novadata != (nova_standard_exception_NovaExceptionData*)nova_null)
	{
		nova_standard_datastruct_NovaArrayList* l2_Novalist;
		int l3_Novai;
		
		l2_Novalist = l1_Novadata->nova_standard_exception_NovaExceptionData_Novacodes;
		l3_Novai = 0;
		for (; l3_Novai < l2_Novalist->nova_standard_datastruct_NovaArrayList_Novasize; l3_Novai++)
		{
			if (((nova_standard_primitive_number_NovaInt*)nova_standard_datastruct_NovaArrayList_Novaget(l2_Novalist, exceptionData, l3_Novai))->nova_standard_primitive_number_NovaInt_Novavalue == l0_Novacode)
			{
				return l1_Novadata;
			}
		}
		if (nova_standard_exception_NovaExceptionData_NovagetParent(l1_Novadata, exceptionData) == (nova_standard_exception_NovaExceptionData*)nova_null)
		{
			return l1_Novadata;
		}
		l1_Novadata = nova_standard_exception_NovaExceptionData_NovagetParent(l1_Novadata, exceptionData);
	}
	return 0;
}

buffer* nova_standard_exception_NovaExceptionData_NovagetCorrectBuffer(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode)
{
	nova_standard_exception_NovaExceptionData* l1_Novadata;
	
	l1_Novadata = nova_standard_exception_NovaExceptionData_NovagetCorrectData(this, exceptionData, l0_Novacode);
	if (l1_Novadata == (nova_standard_exception_NovaExceptionData*)nova_null)
	{
		return 0;
	}
	return nova_standard_exception_NovaExceptionData_NovagetBuffer(l1_Novadata, exceptionData);
}

void nova_standard_exception_NovaExceptionData_NovajumpToBuffer(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode)
{
	nova_standard_exception_NovaExceptionData* l1_Novadata;
	buffer* l1_Novab;
	
	l1_Novadata = nova_standard_exception_NovaExceptionData_NovagetCorrectData(this, exceptionData, l0_Novacode);
	if (nova_standard_exception_NovaExceptionData_NovagetParent(l1_Novadata, exceptionData) == (nova_standard_exception_NovaExceptionData*)nova_null)
	{
		l0_Novacode = 1;
	}
	l1_Novab = nova_standard_exception_NovaExceptionData_NovagetBuffer(l1_Novadata, exceptionData);
	jump(*l1_Novab, l0_Novacode);
}

nova_standard_exception_NovaExceptionData* nova_standard_exception_NovaExceptionData_NovagetParent(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->prv->nova_standard_exception_NovaExceptionData_Novaparent;
}

void nova_standard_exception_NovaExceptionData_NovasetParent(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_exception_NovaExceptionData* l0_Novap)
{
	this->prv->nova_standard_exception_NovaExceptionData_Novaparent = l0_Novap;
}

void nova_standard_exception_NovaExceptionData_Novasuper(nova_standard_exception_NovaExceptionData* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_exception_NovaExceptionData_NovathrownException = (nova_standard_exception_NovaException*)nova_null;
	this->nova_standard_exception_NovaExceptionData_Novacodes = (nova_standard_datastruct_NovaArrayList*)nova_null;
	this->prv->nova_standard_exception_NovaExceptionData_Novabuf = (buffer*)nova_null;
	this->prv->nova_standard_exception_NovaExceptionData_Novaparent = (nova_standard_exception_NovaExceptionData*)nova_null;
}
