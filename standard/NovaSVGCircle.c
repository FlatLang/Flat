#include <precompiled.h>
#include "NovaSVGCircle.h"


nova_VTable_SVGCircle nova_VTable_SVGCircle_val =
{
	nova_0_SVGCircle_generateOutput,
	nova_0_SVGCircle_toString,
};

SVGCircle* nova_SVGCircle_construct(SVGCircle* this, ExceptionData* exceptionData, double nova_0_x, double nova_0_y, int nova_0_r)
{
	CCLASS_NEW(SVGCircle, this,);
	this->vtable = &nova_VTable_SVGCircle_val;
	nova_Object_super((Object*)this, 0);
	nova_SVGComponent_super((SVGComponent*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_SVGComponent_this((SVGComponent*)(this), exceptionData);
	nova_SVGCircle_super(this, 0);
	
	{
		nova_SVGCircle_this(this, exceptionData, nova_0_x, nova_0_y, nova_0_r);
	}
	
	return this;
}

void nova_del_SVGCircle(SVGCircle** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_SVGCircle_this(SVGCircle* this, ExceptionData* exceptionData, double nova_0_x, double nova_0_y, int nova_0_r)
{
	this->nova_SVGCircle_x = nova_0_x;
	this->nova_SVGCircle_y = nova_0_y;
	this->nova_SVGCircle_r = nova_0_r;
}

void nova_0_SVGCircle_generateOutput(SVGCircle* this, ExceptionData* exceptionData, File* nova_0_file)
{
	nova_File_write(nova_0_file, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "<circle cx=\""), exceptionData, nova_String_concat(nova_3_Double_toString(nova_Double_construct(0, exceptionData, this->nova_SVGCircle_x), exceptionData), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "\" cy=\""), exceptionData, nova_String_concat(nova_3_Double_toString(nova_Double_construct(0, exceptionData, this->nova_SVGCircle_y), exceptionData), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "\" r=\""), exceptionData, nova_String_concat(nova_2_Int_toString(nova_Int_construct(0, exceptionData, this->nova_SVGCircle_r), exceptionData), exceptionData, nova_String_construct(0, exceptionData, "\" stroke=\"false\" fill=\"black\"/>\n"))))))));
}

String* nova_0_SVGCircle_toString(SVGCircle* this, ExceptionData* exceptionData)
{
	return nova_String_concat(nova_String_construct(0, exceptionData, "[Circle at ("), exceptionData, nova_String_concat(nova_3_Double_toString(nova_Double_construct(0, exceptionData, this->nova_SVGCircle_x), exceptionData), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, ", "), exceptionData, nova_String_concat(nova_3_Double_toString(nova_Double_construct(0, exceptionData, this->nova_SVGCircle_y), exceptionData), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, ") with a radius of "), exceptionData, nova_String_concat(nova_2_Int_toString(nova_Int_construct(0, exceptionData, this->nova_SVGCircle_r), exceptionData), exceptionData, nova_String_construct(0, exceptionData, "]")))))));
}

void nova_SVGCircle_super(SVGCircle* this, ExceptionData* exceptionData)
{
	this->nova_SVGCircle_x = 0;
	this->nova_SVGCircle_y = 0;
	this->nova_SVGCircle_r = 0;
}
