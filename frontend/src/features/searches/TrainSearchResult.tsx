import TrainSelectItem from "./TrainSelectItem";

function TrainSearchList(){

    return (
    <div className="p-4">
    <div className="text-textPrimary text-2xl font-bold">
        東京→上野
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

export default TrainSearchList;
