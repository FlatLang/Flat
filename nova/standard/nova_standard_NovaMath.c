#include <precompiled.h>
#include <nova/standard/nova_standard_NovaMath.h>


nova_VTable_nova_standard_NovaMath nova_VTable_nova_standard_NovaMath_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
double nova_standard_NovaMath_static_NovaPI;
void nova_standard_NovaMathNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
		nova_standard_NovaMath_static_NovaPI = 3.141592653;
	}
}

nova_standard_NovaMath* nova_standard_NovaMath_Novaconstruct(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaMath, this,);
	this->vtable = &nova_VTable_nova_standard_NovaMath_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaMath_Novasuper(this, 0);
	
	{
		nova_standard_NovaMath_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Math(nova_standard_NovaMath** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_standard_NovaMath_static_Novarandom(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, long l0_Novarange)
{
	return rand() % l0_Novarange;
}

double nova_standard_NovaMath_static_Novaabs(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return abs((double)(l0_Novanumber));
}

double nova_standard_NovaMath_static_Novasqrt(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return sqrt((double)(l0_Novanumber));
}

double nova_standard_NovaMath_static_Novapow(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novabase, double l0_Novapower)
{
	return pow((double)(l0_Novabase), (double)(l0_Novapower));
}

double nova_standard_NovaMath_static_Novasin(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return sin((double)(l0_Novanumber));
}

double nova_standard_NovaMath_static_Novacos(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return cos((double)(l0_Novanumber));
}

double nova_standard_NovaMath_static_Novatan(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return tan((double)(l0_Novanumber));
}

double nova_standard_NovaMath_static_Novaasin(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return asin((double)(l0_Novanumber));
}

double nova_standard_NovaMath_static_Novaacos(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return acos((double)(l0_Novanumber));
}

double nova_standard_NovaMath_static_Novaatan(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return atan((double)(l0_Novanumber));
}

long nova_standard_NovaMath_static_Novaround(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return nova_standard_NovaMath_static_Novafloor((nova_standard_NovaMath*)nova_null, exceptionData, l0_Novanumber + 0.5);
}

long nova_standard_NovaMath_static_Novafloor(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return floor((double)(l0_Novanumber));
}

long nova_standard_NovaMath_static_Novaceil(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	return ceil((double)(l0_Novanumber));
}

void nova_standard_NovaMath_Novathis(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaMath_Novasuper(nova_standard_NovaMath* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
