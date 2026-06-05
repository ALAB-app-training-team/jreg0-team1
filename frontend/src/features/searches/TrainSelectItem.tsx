
function TrainSelectItem(){

    return (
    <div className="flex items-center border border-primary/[20%] rounded-2xl py-8 px-4 gap-4">
        <div className="bg-primary rounded aspect-square flex items-center">
            <span className="material-symbols-outlined text-white">train</span>
        </div>
        <div className="flex-col">
            <h3 className="text-lg text-textPrimary">はやぶさ</h3>
            <h5 className="text-base text-textSecondary">1号</h5>
        </div>
        <div className="flex-col">
            <h2 className="text-2xl text-textPrimary font-bold">06:32</h2>
            <h5 className="text-base text-textSecondary">東京</h5>
        </div>
        <div className="grow h-1 bg-primary/[20%]"></div>
        <div className="flex-col">
            <h2 className="text-2xl text-textPrimary font-bold">06:39</h2>
            <h5 className="text-base text-textSecondary">上野</h5>
        </div>
        <button className="bg-primary rounded-lg text-white p-4">席を予約する</button>
    </div>
    );
}

export default TrainSelectItem;
