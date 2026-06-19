import Modal from 'react-modal';

type SeatSelectModalProps = {
    isOpen: boolean;
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
};

function SeatSelectModal({ isOpen, setIsOpen }: SeatSelectModalProps) {
    return (
        <Modal isOpen={isOpen} onRequestClose={() => setIsOpen(false)}>
            <form>
                <h1>座席選択</h1>
                <div className="flex">
                    <label>
                        <input type="radio" />A
                    </label>
                    <label>
                        <input type="radio" />B
                    </label>
                    <label>
                        <input type="radio" />C
                    </label>
                    <label>
                        <input type="radio" />D
                    </label>
                    <label>
                        <input type="radio" />E
                    </label>
                </div>
            </form>
        </Modal>
    );
}

export default SeatSelectModal;
