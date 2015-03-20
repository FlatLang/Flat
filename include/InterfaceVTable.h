#ifndef NOVA_INTERFACE_VTABLE
#define NOVA_INTERFACE_VTABLE

typedef struct nova_standard_operators_Nova_Multipliable nova_standard_operators_Nova_Multipliable;
typedef struct nova_standard_Nova_Object nova_standard_Nova_Object;
typedef struct nova_standard_Nova_String nova_standard_Nova_String;
typedef struct nova_standard_io_Nova_InputStream nova_standard_io_Nova_InputStream;
typedef struct nova_standard_datastruct_Nova_Comparable nova_standard_datastruct_Nova_Comparable;
typedef struct example_Nova_Polygon example_Nova_Polygon;

typedef struct nova_Interface_VTable
{
nova_standard_Nova_Object* (*nova_standard_operators_Nova_Multipliable_virtual1_Nova_multiply)(nova_standard_operators_Nova_Multipliable*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
void (*nova_standard_operators_Nova_Multipliable_virtual8_Nova_this)(nova_standard_operators_Nova_Multipliable*, nova_standard_exception_Nova_ExceptionData*);
nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual_Nova_getHashCode)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
long (*nova_standard_Nova_Object_virtual3_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual0_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
char (*nova_standard_Nova_Object_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
void (*nova_standard_Nova_Object_virtual2_Nova_this)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
nova_standard_Nova_String* (*nova_standard_io_Nova_InputStream_virtual0_Nova_readString)(nova_standard_io_Nova_InputStream*, nova_standard_exception_Nova_ExceptionData*);
char* (*nova_standard_io_Nova_InputStream_virtual0_Nova_readBytes)(nova_standard_io_Nova_InputStream*, nova_standard_exception_Nova_ExceptionData*);
void (*nova_standard_io_Nova_InputStream_virtual4_Nova_this)(nova_standard_io_Nova_InputStream*, nova_standard_exception_Nova_ExceptionData*);
int (*nova_standard_datastruct_Nova_Comparable_virtual0_Nova_compareTo)(nova_standard_datastruct_Nova_Comparable*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
void (*nova_standard_datastruct_Nova_Comparable_virtual5_Nova_this)(nova_standard_datastruct_Nova_Comparable*, nova_standard_exception_Nova_ExceptionData*);
int (*example_Nova_Polygon_virtual0_Nova_numberSides)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*);
double (*example_Nova_Polygon_virtual0_Nova_calculateArea)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*);
void (*example_Nova_Polygon_virtual2_Nova_this)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*);
} nova_Interface_VTable;

#endif
