const fetcher = <T>(url: string): Promise<T> => {
    const fetchData = fetch(url).then((res) => res.json());
    return fetchData;
};

export default fetcher;
