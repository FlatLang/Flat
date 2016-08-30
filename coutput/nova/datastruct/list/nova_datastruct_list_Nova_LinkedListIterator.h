#pragma once
#ifndef FILE_nova_datastruct_list_Nova_LinkedListIterator_NOVA
#define FILE_nova_datastruct_list_Nova_LinkedListIterator_NOVA

typedef struct nova_datastruct_list_Nova_LinkedListIterator nova_datastruct_list_Nova_LinkedListIterator;


#include <Nova.h>
#include <ExceptionHandler.h>
#include <InterfaceVTable.h>
#include <nova/exception/nova_exception_Nova_ExceptionData.h>
#include <nova/exception/nova_exception_Nova_Exception.h>
#include <nova/exception/nova_exception_Nova_DivideByZeroException.h>
#include <nova/io/nova_io_Nova_Console.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Number.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Byte.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Short.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Int.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Long.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Float.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Double.h>
#include <nova/primitive/nova_primitive_Nova_Null.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Char.h>
#include <nova/primitive/nova_primitive_Nova_Bool.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Array.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_CharArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_DoubleArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntRange.h>
#include <nova/thread/nova_thread_Nova_Thread.h>
#include <nova/thread/async/nova_thread_async_Nova_Async.h>
#include <nova/gc/nova_gc_Nova_GC.h>
#include <nova/math/nova_math_Nova_Math.h>
#include <nova/nova_Nova_Object.h>
#include <nova/nova_Nova_String.h>
#include <nova/nova_Nova_System.h>
#include <nova/nova_Nova_Class.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Iterator.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_LinkedList.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_ListNode.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_NoSuchElementException.h>


typedef struct nova_datastruct_list_Extension_VTable_LinkedListIterator nova_datastruct_list_Extension_VTable_LinkedListIterator;
struct nova_datastruct_list_Extension_VTable_LinkedListIterator
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_Iterator_virtual0_Nova_reset)(nova_datastruct_list_Nova_LinkedListIterator*, nova_exception_Nova_ExceptionData*);
	char (*nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext)(nova_datastruct_list_Nova_LinkedListIterator*, nova_exception_Nova_ExceptionData*);
	nova_Nova_Object* (*nova_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next)(nova_datastruct_list_Nova_LinkedListIterator*, nova_exception_Nova_ExceptionData*);
};

extern nova_datastruct_list_Extension_VTable_LinkedListIterator nova_datastruct_list_Extension_VTable_LinkedListIterator_val;


CCLASS_CLASS
(
	nova_datastruct_list_Nova_LinkedListIterator, 
	
	nova_datastruct_list_Extension_VTable_LinkedListIterator* vtable;
	nova_datastruct_list_Nova_ListNode* nova_datastruct_list_Nova_LinkedListIterator_Nova_position;
	struct Private* prv;
)

void nova_datastruct_list_Nova_LinkedListIterator_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_list_Nova_LinkedListIterator* nova_datastruct_list_Nova_LinkedListIterator_Nova_construct(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_LinkedList* nova_datastruct_list_Nova_LinkedListIterator_Nova_list);
void nova_datastruct_list_Nova_LinkedListIterator_Nova_destroy(nova_datastruct_list_Nova_LinkedListIterator** this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_datastruct_list_Nova_LinkedListIterator_Nova_this(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_LinkedList* nova_datastruct_list_Nova_LinkedListIterator_Nova_list);
nova_datastruct_list_Nova_Iterator* nova_datastruct_list_Nova_LinkedListIterator_0_Nova_reset(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData);
char nova_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_hasNext(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_Object* nova_datastruct_list_Nova_LinkedListIterator_Accessor_Nova_next(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_datastruct_list_Nova_LinkedListIterator_Nova_super(nova_datastruct_list_Nova_LinkedListIterator* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
