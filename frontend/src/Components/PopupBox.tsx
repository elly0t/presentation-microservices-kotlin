import { useState } from "react";
import React from "react";
import { Button, Typography, Modal, Divider } from '@material-ui/core';
import "./style.css"


export default function PopupBox(props: {
    title: string, 
    message: string
}) {
    const [open, setOpen] = useState(true)

    return (
        <Modal 
            open = {open}
            onClose = {() => setOpen(false)}
        >
            <div className="modalBox">

                <Typography variant = "h6">
                    {props.title}
                </Typography>

                <Divider />
                <br />
                <Typography variant = "body1">
                    {props.message}
                </Typography>

                <br />
                <br />
                
                <Button fullWidth variant = "outlined" onClick = {() => setOpen(false)}>
                    Close
                </Button>

                <br />
            </div>

        </Modal>
    )
}