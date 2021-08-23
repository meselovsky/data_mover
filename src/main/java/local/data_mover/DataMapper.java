package local.data_mover;

public interface DataMapper<T, U>
{
    T map(U o);
}
