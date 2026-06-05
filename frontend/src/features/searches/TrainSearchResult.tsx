import TrainSelectItem from "./TrainSelectItem";

function TrainSearchResult(){

    return (
    <div className="p-4 flex flex-col gap-4">
        <h1 className="text-textPrimary text-2xl font-bold">
            東京→上野
        </h1>
        <div className="rounded-2xl bg-primary/[10%] p-4 flex justify-between">
            <div>
                <h5 className="text-base text-textSecondary">出発日</h5>
                <input className="bg-white border border-primary/[20%] rounded px-4 py-2" type="date" title="日付を選択してください" />
            </div>
            <button className="bg-primary rounded-lg text-white p-4 flex items-center">
                <span className="material-symbols-outlined">search</span>
                日付指定
            </button>
        </div>
        <div className="flex justify-end">
            <div className="text-base text-textSecondary">
                n件の列車が見つかりました
            </div>
        </div>
        <TrainSelectItem/>
    </div>
    );
};

export default TrainSearchResult;
