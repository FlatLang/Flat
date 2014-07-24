#ifndef CClass_CClass_h
#define CClass_CClass_h

#ifdef _WIN32
#	define unsigned_long_long unsigned __int64
#	define long_long __int64
#else
#	define unsigned_long_long unsigned long long
#	define long_long long long
#endif

#ifdef USE_GC
#	ifdef _WIN32
#		define GC_WIN32_THREADS
#	elif defined(__APPLE__) || defined(__linux__)
#		define GC_PTHREADS
#	endif

#	define NOVA_FREE GC_free
#	define NOVA_MALLOC GC_malloc
#	define NOVA_REALLOC GC_realloc
#	define free NOVA_FREE
#	define malloc NOVA_MALLOC
#	define realloc NOVA_REALLOC
#else
#	define NOVA_FREE free
#	define NOVA_MALLOC malloc
#	define NOVA_REALLOC realloc
#endif

#define CCLASS_NEW1(_CLASS_) (_CLASS_*)NOVA_MALLOC(sizeof(_CLASS_))
#define CCLASS_NEW3(_CLASS_, _OBJ_, _CCLASS_PRIVATE_)\
	_CLASS_* _OBJ_ = CCLASS_NEW1(_CLASS_);\
	_CCLASS_PRIVATE_
#define CCLASS_NEW2(_CLASS_, _OBJ_) CCLASS_NEW3(_CLASS_, _OBJ_, _OBJ_->prv = new_Private())

#define CCLASS_CLASS_DEF(_NAME_, _BODY_) struct _NAME_ { _BODY_ }
#define CCLASS_CLASS2(_NAME_, _BODY_) CCLASS_CLASS_DEF(_NAME_, _BODY_)
#define CCLASS_CLASS1(_NAME_) CCLASS_CLASS2(_NAME_,)


#ifdef TCC
#	define GET_MACRO2P(_1_, _2_, NAME, ...) NAME
#	define GET_MACRO3P(_1_, _2_, _3_, NAME, ...) NAME
#	define GET_MACRO4P(_1_, _2_, _3_, _4_, NAME, ...) NAME
#	define GET_MACRO5P(_1_, _2_, _3_, _4_, _5_, NAME, ...) NAME
	
#	define CCLASS_NEW(...) GET_MACRO3P(__VA_ARGS__, CCLASS_NEW3, CCLASS_NEW2, CCLASS_NEW1)(__VA_ARGS__)
#	define CCLASS_CLASS(...) GET_MACRO2P(__VA_ARGS__, CCLASS_CLASS2, CCLASS_CLASS1)(__VA_ARGS__);
#else
#	define GLUE(x, y) x y

#	define RETURN_ARG_COUNT(_1_, _2_, _3_, _4_, _5_, count, ...) count
#	define EXPAND_ARGS(args) RETURN_ARG_COUNT args
#	define COUNT_ARGS_MAX5(...) EXPAND_ARGS((__VA_ARGS__, 5, 4, 3, 2, 1, 0))

#	define OVERLOAD_MACRO2(name, count) name##count
#	define OVERLOAD_MACRO1(name, count) OVERLOAD_MACRO2(name, count)
#	define OVERLOAD_MACRO(name, count) OVERLOAD_MACRO1(name, count)

#	define CALL_OVERLOAD(name, ...) GLUE(OVERLOAD_MACRO(name, COUNT_ARGS_MAX5(__VA_ARGS__)), (__VA_ARGS__))

#	define CCLASS_NEW(...) CALL_OVERLOAD(CCLASS_NEW, __VA_ARGS__)
#	define CCLASS_CLASS(...) CALL_OVERLOAD(CCLASS_CLASS, __VA_ARGS__);
#endif


#define CCLASS_DELETE(_OBJ_) NOVA_FREE(_OBJ_)
#define CCLASS_DEL(_OBJ_) CCLASS_DELETE(_OBJ_->prv); CCLASS_DELETE(_OBJ_)
#define CCLASS_FUNC(_TYPE_, _NAME_, ...) _TYPE_ (*_NAME_)(__VA_ARGS__)

#define CCLASS_INSTANTIATE(_CLASS_, _OBJ_, _VARIABLE_INITIALIZE_)\
	do\
	{\
		_CLASS_* _OBJ_ = CCLASS_NEW1(_CLASS_);\
		assert(_OBJ_);\
		\
		{\
			_VARIABLE_INITIALIZE_;\
		}\
		\
		return _OBJ_;\
	} while (0)

#define CCLASS_PRIVATE(_CCLASS_PRIVATE_VARIABLES_)\
	typedef struct Private { _CCLASS_PRIVATE_VARIABLES_ } Private;\
	\
	static Private* new_Private()\
	{\
		CCLASS_INSTANTIATE(Private, p,);\
	}\
	static void del_Private(Private* p)\
	{\
		CCLASS_DELETE(p);\
	}

#endif
