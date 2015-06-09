package handa.beans.converters;


public interface BeanConverter<From, To>
{
    To convert(From obj);
    From convertBack(To obj);
}
